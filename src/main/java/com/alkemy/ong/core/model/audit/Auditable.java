package com.alkemy.ong.core.model.audit;

public interface Auditable {
    Audit getAudit();

    void setAudit(Audit audit);
}
