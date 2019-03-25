/**
 * 菜单信息
 */
common.pageName = "financeManage";
common.openName = ['financeManage'];

var vue = new Vue({
    el: '#financeManage',
    data: {
        formInline: {
            id: -1,
            projectId: null,
            projectName: null,
            person: null
        },
        addForm: {},
        queryParam: {
            condition: {},
            pageNum: 1,
            pageSize: 10
        },
        pageInfo: {},
        statusItems: [],
        models: [],
        project: {
            projectId: "",
            detail: {
                loanInstallments: []
            }
        },
        loanInstallment: {
            amount: 0,
            detail: {
                id: 0
            },
            installmentDate: ""
        },

    },
    created: function () {
        this.initDate();
        this.query();
    },
    methods: {
        /**
         * 初始化数据
         */
        initDate: function () {
            let _self = this;
            this.$http.get("/combo/progress").then(function (response) {
                _self.statusItems = response.data;
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/projectModel").then(function (response) {
                _self.models = response.data;
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/steps").then(function (response) {
                _self.steps = response.data;
            }, function (error) {
                console.error(error);
            });
        },
        query: function () {
            let self = this;
            self.queryParam.condition = self.formInline;
            this.$http.post("/financeManage/query", self.queryParam).then(
                function (response) {
                    let data = response.data;
                    //console.log(data);
                    self.pageInfo = data;
                }, function (error) {
                    self.$Message.error(error.data.message);
                })
        },

        /**
         * 编辑项目
         */
        edit: function (id) {
            window.open("financeOperation?id=" + id, "_self");

        },

        /**
         * 翻译
         */
        toModelName: function (value) {
            for (let index in this.models) {
                if (this.models[index].value == value) {
                    return this.models[index].text;
                }
            }
            return "";
        },

        /**
         * 翻译
         */
        toStepName: function (value) {
            for (let index in this.steps) {
                if (this.steps[index].value == value) {
                    return this.steps[index].text;
                }
            }
            return "";
        },

        toBoolean: function (value) {
            if (value) {
                return "是";
            }
            return "否";
        },

        /**
         * 状态翻译
         */
        getProgress: function (value) {
            for (var index in this.statusItems) {
                if (value == this.statusItems[index].value) {
                    return this.statusItems[index].text;
                }
            }
            return "";
        },

        /** 搜索 */
        search: function () {
            this.queryParam.pageNum = 1;
            this.query();
        },

        /** 分页 */
        pageChange: function (page) {
            this.queryParam.pageNum = page;
            this.query();
        },

        /**
         * 重置
         */
        reset: function () {
            this.$refs['searchForm'].resetFields();
        },
        columnRender: function (h, param) {
            let childArray = param.row[param.column.key];
            if (childArray.length == 0) {
                return h('span');
            } else if (childArray.length == 1) {
                let value = childArray[0];
                if (param.column.colType == 'boolean') {
                    value = this.toBoolean(value);
                }
                return h('span', value);
            } else {
                let trAarry = [];
                childArray.forEach((item, index) => {
                    if (param.column.colType == 'boolean') {
                        item = this.toBoolean(item);
                    }

                    let aa = h('tr', {}, [
                        h('td', {
                            style: {
                                border: 0,
                                'text-align': 'center',
                                'text-align': 'center',
                                'vertical-align': 'middle',
                                'width': '100%'
                            }
                        }, item),
                    ]);
                    trAarry.push(aa);
                    if (index !== childArray.length - 1) {
                        let bb = h('hr', {
                            style: {
                                height: '1px',
                                'background-color': '#e9eaec',
                                border: 'none'
                            }
                        });
                        trAarry.push(bb)
                    }
                });
                return h('table', {
                    style: {
                        'width': '100%',
                        margin: 0,
                        border: 0
                    }
                }, trAarry);
            }
        }
    }
});

vue.tableColumns = [{
    title: '项目编号',
    key: 'projectId',
    align: 'center',
    width: 100
}, {
    title: '项目名称',
    key: 'projectName',
    align: 'center',
    width: 100
}, {
    title: '业务负责人',
    key: 'username',
    align: 'center',
}, {
    title: '放款审核通过时间',
    key: 'loanAuditPassTime',
    tooltip: true,
    align: 'center',
    width: 105
}, {
    title: '应收账款（万元）',
    key: 'receivable',
    align: 'center',
}, {
    title: '已投放金额（万元）',
    key: 'dropAmount',
    align: 'center',
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '投放日期',
    key: 'dropDates',
    align: 'center',
    width: 105,
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '回款金额（万元）',
    key: 'returnAmount',
    align: 'center',
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '回款日期',
    key: 'returnDates',
    align: 'center',
    width: 105,
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '保理费合计（万元）',
    key: 'totalFactoringFee',
    align: 'center',
}, {
    title: '保理费分期（万元）',
    key: 'factoringInstallmentAmounts',
    align: 'center',
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '保理费到账日（前）',
    key: 'factoringInstallmentDates',
    align: 'center',
    width: 105,
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '是否已开发票',
    key: 'factoringInstallmentInvoiceds',
    align: 'center',
    colType: 'boolean',
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '操作',
    align: 'center',
    render: (h, param) => {
        return h('div', [
                h('span'),
                h('Button', {
                    props: {
                        size: "small",
                        type: "warning",
                        ghost: true
                    },
                    style: {
                        marginRight: '5px'
                    },
                    on: {
                        click: () => {
                            vue.edit(param.row.id);
                        }
                    }
                }, '编辑')
            ]
        )
    }
}
];


