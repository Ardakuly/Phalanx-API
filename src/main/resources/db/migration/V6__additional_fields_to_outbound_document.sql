ALTER TABLE outbound_document ADD COLUMN payment_type SMALLINT NOT NULL DEFAULT 1;
ALTER TABLE outbound_document ADD COLUMN seller_id VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE outbound_document ADD COLUMN comment VARCHAR(500);

UPDATE outbound_document SET payment_type = 1;
UPDATE outbound_document SET seller_id = '3d32c50e-0d91-4808-96ea-676fc3d11e5d';

ALTER TABLE outbound_document
ADD CONSTRAINT fk_outbound_document_users
FOREIGN KEY (seller_id)
REFERENCES users(id);

COMMENT ON COLUMN outbound_document.payment_type IS 'Payment Type.';
COMMENT ON COLUMN outbound_document.seller_id IS 'Seller who sold products.';
COMMENT ON COLUMN outbound_document.comment IS 'Additional info about sold products.';