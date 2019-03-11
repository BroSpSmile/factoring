package com.smile.start.event;

import com.smile.start.model.project.Audit;
import org.springframework.context.ApplicationEvent;

public class FlowEvent extends ApplicationEvent {

    private static final long serialVersionUID = -5642665846821303473L;

    private Audit audit;

    /**
     * 在自定义事件的构造方法中除了第一个source参数，其他参数都可以去自定义，
     *
     * @param source
     * @param audit
     */
    public FlowEvent(Object source, Audit audit) {
        super(source);
        this.audit = audit;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

}
