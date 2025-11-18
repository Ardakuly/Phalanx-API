-- =========================================
-- Table: category
-- Stores product categories
-- =========================================
CREATE TABLE category (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR(255)
);

COMMENT ON TABLE category IS 'Stores product categories';
COMMENT ON COLUMN category.id IS 'Primary key of the category';
COMMENT ON COLUMN category.name IS 'Name of the category';

-- =========================================
-- Table: product
-- Stores product details
-- =========================================
CREATE TABLE product (
     id BIGSERIAL PRIMARY KEY,
     external_id VARCHAR(255) NOT NULL UNIQUE,
     name VARCHAR(255) NOT NULL,
     sku VARCHAR(255),
     barcode VARCHAR(255) UNIQUE,
     unit SMALLINT NOT NULL,
     category_id BIGINT NOT NULL REFERENCES category(id) ON DELETE RESTRICT,
     purchased_price NUMERIC(19,4) NOT NULL,
     selling_price NUMERIC(19,4) NOT NULL,
     stock_balance NUMERIC(19,4) NOT NULL,
     photo_url VARCHAR(1000),
     created_at TIMESTAMP NOT NULL DEFAULT NOW(),
     updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE product IS 'Stores product details including pricing, stock, and category';
COMMENT ON COLUMN product.id IS 'Primary key of the product';
COMMENT ON COLUMN product.external_id IS 'External reference ID for the product';
COMMENT ON COLUMN product.name IS 'Name of the product';
COMMENT ON COLUMN product.sku IS 'Stock Keeping Unit';
COMMENT ON COLUMN product.barcode IS 'Unique barcode of the product';
COMMENT ON COLUMN product.unit IS 'Unit of measurement for the product (e.g., piece, kg)';
COMMENT ON COLUMN product.category_id IS 'Reference to category table';
COMMENT ON COLUMN product.purchased_price IS 'Price at which the product was purchased';
COMMENT ON COLUMN product.selling_price IS 'Price at which the product is sold';
COMMENT ON COLUMN product.stock_balance IS 'Current stock balance of the product';
COMMENT ON COLUMN product.photo_url IS 'URL for product image';
COMMENT ON COLUMN product.created_at IS 'Timestamp when product was created';
COMMENT ON COLUMN product.updated_at IS 'Timestamp when product was last updated';
