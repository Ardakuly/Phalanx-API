CREATE TABLE outbound_document (
       id BIGSERIAL PRIMARY KEY,
       external_id VARCHAR(255) NOT NULL,
       document_number VARCHAR(255) NOT NULL,
       created_at TIMESTAMP NOT NULL,
       updated_at TIMESTAMP NOT NULL
);

COMMENT ON TABLE outbound_document IS 'Represents an outbound document containing sold goods.';

COMMENT ON COLUMN outbound_document.id IS 'Primary key.';
COMMENT ON COLUMN outbound_document.external_id IS 'External system identifier.';
COMMENT ON COLUMN outbound_document.document_number IS 'Human-readable outbound document number.';
COMMENT ON COLUMN outbound_document.created_at IS 'Creation timestamp.';
COMMENT ON COLUMN outbound_document.updated_at IS 'Last update timestamp.';

CREATE TABLE inbound_document (
      id BIGSERIAL PRIMARY KEY,
      external_id VARCHAR(255) NOT NULL UNIQUE,
      document_number VARCHAR(255) NOT NULL UNIQUE,
      created_at TIMESTAMP NOT NULL,
      updated_at TIMESTAMP NOT NULL
);

COMMENT ON TABLE inbound_document IS 'Represents an inbound document containing received goods.';

COMMENT ON COLUMN inbound_document.id IS 'Primary key.';
COMMENT ON COLUMN inbound_document.external_id IS 'Unique external identifier for inbound document.';
COMMENT ON COLUMN inbound_document.document_number IS 'Unique inbound document number.';
COMMENT ON COLUMN inbound_document.created_at IS 'Creation timestamp.';
COMMENT ON COLUMN inbound_document.updated_at IS 'Last update timestamp.';

CREATE TABLE inbound_good (
      id BIGSERIAL PRIMARY KEY,
      external_id VARCHAR(255) NOT NULL UNIQUE,
      name VARCHAR(255) NOT NULL,
      sku VARCHAR(255),
      barcode VARCHAR(255) UNIQUE,
      unit VARCHAR(255) NOT NULL,
      category_id BIGINT NOT NULL,
      purchased_price NUMERIC NOT NULL,
      selling_price NUMERIC NOT NULL,
      quantity NUMERIC NOT NULL,
      photo_url VARCHAR(255),
      inbound_document_id BIGINT NOT NULL,
      created_at TIMESTAMP NOT NULL,
      updated_at TIMESTAMP NOT NULL,

      CONSTRAINT fk_inbound_good_category
          FOREIGN KEY (category_id) REFERENCES category(id),

      CONSTRAINT fk_inbound_good_inbound_document
          FOREIGN KEY (inbound_document_id) REFERENCES inbound_document(id)
);

COMMENT ON TABLE inbound_good IS 'Represents a single item received in an inbound document.';

COMMENT ON COLUMN inbound_good.id IS 'Primary key.';
COMMENT ON COLUMN inbound_good.external_id IS 'Unique external identifier for the inbound good.';
COMMENT ON COLUMN inbound_good.name IS 'Product name.';
COMMENT ON COLUMN inbound_good.sku IS 'Optional SKU code.';
COMMENT ON COLUMN inbound_good.barcode IS 'Unique barcode of the product.';
COMMENT ON COLUMN inbound_good.unit IS 'Unit of measurement (stored as string via converter).';
COMMENT ON COLUMN inbound_good.category_id IS 'Foreign key referencing category.';
COMMENT ON COLUMN inbound_good.purchased_price IS 'Purchase price of the product.';
COMMENT ON COLUMN inbound_good.selling_price IS 'Selling price of the product.';
COMMENT ON COLUMN inbound_good.quantity IS 'Quantity received.';
COMMENT ON COLUMN inbound_good.photo_url IS 'Optional URL of the product photo.';
COMMENT ON COLUMN inbound_good.inbound_document_id IS 'Foreign key referencing the inbound document.';
COMMENT ON COLUMN inbound_good.created_at IS 'Creation timestamp.';
COMMENT ON COLUMN inbound_good.updated_at IS 'Last update timestamp.';

CREATE TABLE outbound_good (
       id BIGSERIAL PRIMARY KEY,
       external_id VARCHAR(255) NOT NULL UNIQUE,
       name VARCHAR(255) NOT NULL,
       sku VARCHAR(255),
       barcode VARCHAR(255) UNIQUE,
       unit VARCHAR(255) NOT NULL,
       category_id BIGINT NOT NULL,
       purchased_price NUMERIC NOT NULL,
       selling_price NUMERIC NOT NULL,
       quantity NUMERIC NOT NULL,
       photo_url VARCHAR(255),
       outbound_document_id BIGINT NOT NULL,
       created_at TIMESTAMP NOT NULL,
       updated_at TIMESTAMP NOT NULL,

       CONSTRAINT fk_outbound_good_category
           FOREIGN KEY (category_id) REFERENCES category(id),

       CONSTRAINT fk_outbound_good_outbound_document
           FOREIGN KEY (outbound_document_id) REFERENCES outbound_document(id)
);

COMMENT ON TABLE outbound_good IS 'Represents a single item sold in an outbound document.';

COMMENT ON COLUMN outbound_good.id IS 'Primary key.';
COMMENT ON COLUMN outbound_good.external_id IS 'Unique external identifier for the outbound good.';
COMMENT ON COLUMN outbound_good.name IS 'Product name.';
COMMENT ON COLUMN outbound_good.sku IS 'Optional SKU code.';
COMMENT ON COLUMN outbound_good.barcode IS 'Unique barcode of the product.';
COMMENT ON COLUMN outbound_good.unit IS 'Unit of measurement (stored as string via converter).';
COMMENT ON COLUMN outbound_good.category_id IS 'Foreign key referencing category.';
COMMENT ON COLUMN outbound_good.purchased_price IS 'Purchase price of the product.';
COMMENT ON COLUMN outbound_good.selling_price IS 'Selling price of the product.';
COMMENT ON COLUMN outbound_good.quantity IS 'Quantity sold.';
COMMENT ON COLUMN outbound_good.photo_url IS 'Optional URL of the product photo.';
COMMENT ON COLUMN outbound_good.outbound_document_id IS 'Foreign key referencing the outbound document.';
COMMENT ON COLUMN outbound_good.created_at IS 'Creation timestamp.';
COMMENT ON COLUMN outbound_good.updated_at IS 'Last update timestamp.';
