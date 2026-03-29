CREATE TABLE good_return_document (
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR(255) NOT NULL UNIQUE,
    document_number VARCHAR(255) NOT NULL UNIQUE,
    outbound_document_id BIGINT NOT NULL,
    refund_amount NUMERIC NOT NULL,
    comment TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_good_return_outbound_document
        FOREIGN KEY (outbound_document_id) REFERENCES outbound_document(id)
);

COMMENT ON TABLE good_return_document IS 'Represents a document for goods returned from an outbound document.';
COMMENT ON COLUMN good_return_document.id IS 'Primary key.';
COMMENT ON COLUMN good_return_document.external_id IS 'Unique external identifier for the return document.';
COMMENT ON COLUMN good_return_document.document_number IS 'Unique human-readable document number.';
COMMENT ON COLUMN good_return_document.outbound_document_id IS 'Reference to the original outbound document.';
COMMENT ON COLUMN good_return_document.refund_amount IS 'Total amount to be refunded.';
COMMENT ON COLUMN good_return_document.comment IS 'Optional comment on the return.';
COMMENT ON COLUMN good_return_document.created_at IS 'Creation timestamp.';
COMMENT ON COLUMN good_return_document.updated_at IS 'Last update timestamp.';

CREATE TABLE returned_good (
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(255),
    unit INTEGER NOT NULL,
    category_id BIGINT NOT NULL,
    purchased_price NUMERIC NOT NULL,
    selling_price NUMERIC NOT NULL,
    quantity NUMERIC NOT NULL,
    good_return_document_id BIGINT NOT NULL,
    outbound_good_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_returned_good_category
        FOREIGN KEY (category_id) REFERENCES category(id),

    CONSTRAINT fk_returned_good_document
        FOREIGN KEY (good_return_document_id) REFERENCES good_return_document(id),

    CONSTRAINT fk_returned_good_outbound_good
        FOREIGN KEY (outbound_good_id) REFERENCES outbound_good(id)
);

COMMENT ON TABLE returned_good IS 'Represents a single item returned in a good return document.';
COMMENT ON COLUMN returned_good.id IS 'Primary key.';
COMMENT ON COLUMN returned_good.external_id IS 'Unique external identifier for the returned good.';
COMMENT ON COLUMN returned_good.name IS 'Product name at the time of return.';
COMMENT ON COLUMN returned_good.barcode IS 'Barcode of the returned product.';
COMMENT ON COLUMN returned_good.unit IS 'Unit of measurement (stored as integer code).';
COMMENT ON COLUMN returned_good.category_id IS 'Reference to the category.';
COMMENT ON COLUMN returned_good.purchased_price IS 'Purchase price recorded at the time of sale.';
COMMENT ON COLUMN returned_good.selling_price IS 'Selling price recorded at the time of sale.';
COMMENT ON COLUMN returned_good.quantity IS 'Quantity being returned.';
COMMENT ON COLUMN returned_good.good_return_document_id IS 'Reference to the good return document.';
COMMENT ON COLUMN returned_good.outbound_good_id IS 'Optional reference to the original outbound good record.';
COMMENT ON COLUMN returned_good.created_at IS 'Creation timestamp.';
COMMENT ON COLUMN returned_good.updated_at IS 'Last update timestamp.';
