ALTER TABLE bank_card
    ADD CONSTRAINT check_security_code CHECK (security_code BETWEEN 100 AND 9999);
ALTER TABLE bank_card
    ADD CONSTRAINT check_card_number CHECK (CHAR_LENGTH(card_number) BETWEEN 8 AND 19);