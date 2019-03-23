package com.smile.start.event;

import com.smile.start.model.project.Project;
import org.springframework.context.ApplicationEvent;

public class InstallmentEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2811401486809223884L;

    /** 审核信息 */
    private Project             project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * 在自定义事件的构造方法中除了第一个source参数，其他参数都可以去自定义，
     *
     * @param source
     * @param project
     */
    public InstallmentEvent(Object source, Project project) {
        super(source);
        this.project = project;
    }

}
