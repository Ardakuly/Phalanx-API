package com.backbone.phalanx.receipt.service.implementation;

import com.backbone.phalanx.good_return_document.model.GoodReturnDocument;
import com.backbone.phalanx.good_return_document.model.ReturnedGood;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.receipt.configuration.PrinterConfig;
import com.backbone.phalanx.receipt.service.ReceiptService;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.output.PrinterOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    private final PrinterConfig printerConfig;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    @Async
    public void printReceipt(OutboundDocument document) {

        PrintService printService = findPrintService(printerConfig.getName());
        if (printService == null) {
            log.warn("No suitable printer found. Skipping printing.");
            return;
        }

        try (PrinterOutputStream outputStream = new PrinterOutputStream(printService);
             EscPos escpos = new EscPos(outputStream)) {

            escpos.setCharacterCodeTable(EscPos.CharacterCodeTable.CP866_Cyrillic_2);

            Style titleStyle = new Style()
                    .setFontSize(Style.FontSize._2, Style.FontSize._2)
                    .setJustification(EscPosConst.Justification.Center)
                    .setBold(true);

            Style subtitleStyle = new Style()
                    .setJustification(EscPosConst.Justification.Center);

            Style boldStyle = new Style().setBold(true);

            escpos.writeLF(titleStyle, "PHALANX POS");
            escpos.writeLF(subtitleStyle, "Спасибо за покупку!");
            escpos.writeLF("--------------------------------");
            escpos.writeLF("Чек No:  " + document.getDocumentNumber());
            escpos.writeLF("Дата:    " + document.getCreatedAt().format(FORMATTER));
            escpos.writeLF("Продавец: " + document.getSeller().getFullName());
            escpos.writeLF("Оплата:   " + document.getPaymentType().getName());
            escpos.writeLF("--------------------------------");

            BigDecimal total = BigDecimal.ZERO;
            for (OutboundGood good : document.getOutboundGoods()) {
                String itemName = good.getName();
                BigDecimal qty = good.getQuantity();
                BigDecimal price = good.getSellingPrice();
                BigDecimal subTotal = price.multiply(qty);
                total = total.add(subTotal);

                escpos.writeLF(itemName);
                escpos.writeLF(String.format("  %s x %s = %s", qty, price, subTotal));
            }

            escpos.writeLF("--------------------------------");
            escpos.writeLF(boldStyle, String.format("ИТОГО: %s", total));
            escpos.writeLF("--------------------------------");
            escpos.feed(3);
            escpos.cut(EscPos.CutMode.FULL);

            log.info("Receipt printed successfully for document: {}", document.getDocumentNumber());

        } catch (IOException e) {
            log.error("Failed to print receipt for document: {}", document.getDocumentNumber(), e);
        }
    }

    @Override
    @Async
    public void printReturnReceipt(GoodReturnDocument document) {
        PrintService printService = findPrintService(printerConfig.getName());
        if (printService == null) {
            log.warn("No suitable printer found. Skipping printing.");
            return;
        }

        try (PrinterOutputStream outputStream = new PrinterOutputStream(printService);
             EscPos escpos = new EscPos(outputStream)) {

            escpos.setCharacterCodeTable(EscPos.CharacterCodeTable.CP866_Cyrillic_2);

            Style titleStyle = new Style()
                    .setFontSize(Style.FontSize._2, Style.FontSize._2)
                    .setJustification(EscPosConst.Justification.Center)
                    .setBold(true);

            Style subtitleStyle = new Style()
                    .setJustification(EscPosConst.Justification.Center);

            Style boldStyle = new Style().setBold(true);

            // Header
            escpos.writeLF(titleStyle, "PHALANX POS");
            escpos.writeLF(subtitleStyle, "ВОЗВРАТ ТОВАРА");
            escpos.writeLF("--------------------------------");
            escpos.writeLF("Возврат No: " + document.getDocumentNumber());
            escpos.writeLF("Чек No:     " + document.getOutboundDocument().getDocumentNumber());
            escpos.writeLF("Дата:       " + document.getCreatedAt().format(FORMATTER));
            escpos.writeLF("Продавец:   " + document.getOutboundDocument().getSeller().getFullName());
            escpos.writeLF("--------------------------------");

            // Items
            for (ReturnedGood good : document.getReturnedGoods()) {
                String itemName = good.getName();
                BigDecimal qty = good.getQuantity();
                BigDecimal price = good.getSellingPrice();
                BigDecimal subTotal = price.multiply(qty);

                escpos.writeLF(itemName);
                escpos.writeLF(String.format("  %s x %s = %s", qty, price, subTotal));
            }

            escpos.writeLF("--------------------------------");
            escpos.writeLF(boldStyle, String.format("СУММА ВОЗВРАТА: %s", document.getRefundAmount()));
            escpos.writeLF("--------------------------------");
            escpos.feed(3);
            escpos.cut(EscPos.CutMode.FULL);

            log.info("Return receipt printed successfully for document: {}", document.getDocumentNumber());

        } catch (IOException e) {
            log.error("Failed to print return receipt for document: {}", document.getDocumentNumber(), e);
        }
    }

    private PrintService findPrintService(String printerName) {

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        if (printerName != null && !printerName.isEmpty()) {
            for (PrintService service : printServices) {
                if (service.getName().equalsIgnoreCase(printerName)) {
                    return service;
                }
            }
        }

        for (PrintService service : printServices) {
            String name = service.getName().toLowerCase();
            if (name.contains("receipt") || name.contains("pos") || name.contains("thermal")) {
                return service;
            }
        }

        return PrintServiceLookup.lookupDefaultPrintService();
    }
}