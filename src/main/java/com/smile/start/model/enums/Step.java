package com.smile.start.model.enums;

/**
 * 项目步骤
 *
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum Step {

                  /**
                  * 立项
                  * @author smile.jing
                  * @version $Id: Step.java, v 0.1 Mar 10, 2019 9:36:46 PM smile.jing Exp $
                  */
                  APPROVAL(0, "立项") {

                      /** 
                       * @see com.smile.start.model.enums.Step#next()
                       */
                      @Override
                      public Step next() {
                          return null;
                      }

                      /** 
                       * @see com.smile.start.model.enums.Step#prev()
                       */
                      @Override
                      public Step prev() {
                          return null;
                      }

                  };

    /** 步骤计数器 */
    private int    index;

    /** 步骤名称 */
    private String name;

    /**
     * 构造函数
     * @param step
     * @param name
     */
    Step(int index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * Getter method for property <tt>index</tt>.
     * 
     * @return property value of index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取步骤
     * @param index
     * @return
     */
    public static Step getStep(int index) {
        Step[] steps = Step.values();
        for (Step step : steps) {
            if (step.getIndex() == index) {
                return step;
            }
        }
        return null;
    }

    /**
     * 上一步
     * @return
     */
    public abstract Step next();

    /**
     * 下一步
     * @return
     */
    public abstract Step prev();
}
