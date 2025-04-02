package com.genesis.model;

import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RECORD")
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
@Builder
public class Record {

    @Id
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "SOURCE", nullable = false)
    private String source;

    @Column(name = "DISPLAY_VALUE", nullable = false)
    private String displayValue;

    @Column(name = "LONG_DESCRIPTION")
    private String longDescription;

    @Column(name = "FROM_DATE", nullable = false)
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "SORTING_PRIORITY")
    private Long sortingPriority;

    @Column(name = "CODE_LIST_CODE")
    private String codeListCode;

    @Override
    public int hashCode() {
        int result = 17;  // Start with a non-zero prime number
        result = 31 * result + (code == null ? 0 : code.hashCode());
        result = 31 * result + (source == null ? 0 : source.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Record record = (Record) obj;
        return Objects.equals(this.code, record.code) && Objects.equals(this.source, record.source);
    }

}
