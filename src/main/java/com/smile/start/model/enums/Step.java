package com.smile.start.model.enums;

/**
 * 项目步骤
 *
 * @author smile.jing
 * @version $Id: TuneUpFlowEnum.java, v 0.1 Mar 5, 2019 12:30:14 PM smile.jing Exp $
 */
public enum Step {

                  /**
                  * 业务申请
                  * @author smile.jing
                  * @version $Id: Step.java, v 0.1 Mar 10, 2019 9:36:46 PM smile.jing Exp $
                  */
                  APPROVAL(0, "业务申请") {

                      /** 
                       * @see com.smile.start.model.enums.Step#prev()
                       */
                      @Override
                      public Step prev() {
                          return null;
                      }

                      /** 
                       * @see com.smile.start.model.enums.Step#next()
                       */
                      @Override
                      public Step next() {
                          return TUNEUP;
                      }

                  },
                  TUNEUP(1, "业务审核") {

                      /** 
                       * @see com.smile.start.model.enums.Step#prev()
                       */
                      @Override
                      public Step prev() {
                          return APPROVAL;
                      }

                      /** 
                       * @see com.smile.start.model.enums.Step#next()
                       */
                      @Override
                      public Step next() {
                          return LOAN;
                      }

                  },
                  LOAN(2, "放款录入") {

                      /** 
                       * @see com.smile.start.model.enums.Step#prev()
                       */
                      @Override
                      public Step prev() {
                          return TUNEUP;
                      }

                      /** 
                       * @see com.smile.start.model.enums.Step#next()
                       */
                      @Override
                      public Step next() {
                          return AFTER_LOAN;
                      }

                  },
                  AFTER_LOAN(3, "贷后管理") {
                      /** 
                       * @see com.smile.start.model.enums.Step#prev()
                       */
                      @Override
                      public Step prev() {
                          return LOAN;
                      }

                      /** 
                       * @see com.smile.start.model.enums.Step#next()
                       */
                      @Override
                      public Step next() {
                          return FILE;
                      }

                  },
                  FILE(4, "归档") {

                      /** 
                       * @see com.smile.start.model.enums.Step#prev()
                       */
                      @Override
                      public Step prev() {
                          return AFTER_LOAN;
                      }

                      /** 
                       * @see com.smile.start.model.enums.Step#next()
                       */
                      @Override
                      public Step next() {
                          return null;
                      }

                  };

    /** 步骤计数器 */
    private int    index;

    /** 步骤名称 */
    private String name;

    /**
     * 构造函数
     * @param index
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
    public abstract Step prev();

    /**
     * 下一步步
     * @return
     */
    public abstract Step next();

}
