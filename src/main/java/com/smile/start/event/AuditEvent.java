package com.smile.start.event;

import com.smile.start.model.project.Audit;
import org.springframework.context.ApplicationEvent;

public class AuditEvent extends ApplicationEvent {

    private static final long serialVersionUID = -5642665846821303473L;

    /** 审核信息 */
    private Audit             audit;

    /**
     * 在自定义事件的构造方法中除了第一个source参数，其他参数都可以去自定义，
     *
     * @param source
     * @param audit
     */
    public AuditEvent(Object source, Audit audit) {
        super(source);
        this.audit = audit;
    }

    /**
     * Getter method for property <tt>audit</tt>.
     * 
     * @return property value of audit
     */
    public Audit getAudit() {
        return audit;
    }

    /**
     * Setter method for property <tt>audit</tt>.
     * 
     * @param audit value to be assigned to property audit
     */
    public void setAudit(Audit audit) {
        this.audit = audit;
    }

}
