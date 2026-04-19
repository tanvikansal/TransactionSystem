CREATE TABLE transactions (
                              transaction_id BIGSERIAL PRIMARY KEY,

                              account_id UUID NOT NULL,

                              operation_type_id INT NOT NULL,

                              amount NUMERIC(19, 2) NOT NULL,

                              event_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);