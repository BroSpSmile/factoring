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
        entrustModal: false,
        entrustForm: {
            projectId: -1,
            toUserId: '',
            remark: '',
        },
        isFinanceAdmin: false,
        financeUserList: [],
        tableColumns:[],
        indexsteps:[]
    },
    created: function () {
        this.initDate();
        //this.query();
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
            this.$http.get("/financeManage/isFinanceAdmin").then(function (response) {
                let data = response.data;
                this.isFinanceAdmin = data.data;
                this.query();
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/financeManage/financeUserList").then(function (response) {
                this.financeUserList = response.data;
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/indexsteps").then(function(response) {
				_self.indexsteps = response.data;
			}, function(error) {
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
            // parent.window.menu.createNew({
            //     name: "财务管理",
            //     url: "financeOperation?id=" + id,
            //     id: "financeOperation"
            // });
            // //window.open(this.projectUrl, "_self");
            window.location.href="financeOperation?id=" + id;
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

        /**
         * 流程流转弹框
         */
        entrustModel: function (id) {
            let _self = this;
            this.entrustForm = {
                projectId: id,
                toUserId: '',
                remark: '',
            };
            _self.entrustModal = true;
            this.$http.get("/financeManage/entrustAuth/" + id).then(function (response) {
                let entrustAuth = response.data.data;
                if (null != entrustAuth) {
                    _self.entrustForm = entrustAuth;
                    _self.entrustForm.toUserId = _self.entrustForm.toUserId + '';
                }
            }, function (error) {
                console.error(error);
            });
        },

        /**
         * 委托确认
         */
        entrustConfirm: function () {
            let self = this;
            this.$Spin.show();
            self.entrustForm.type='LOANEN';
            this.$http.post("/financeManage/entrustAuth", self.entrustForm).then(function (response) {
                this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.success({
                        content: "流程流转成功",
                        onClose: function () {
                            window.close();
                        },
                    });
                    self.entrustCancel();
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                this.$Spin.hide();
                self.$Message.error(error);
            })
        },

        /**
         * 委托取消
         */
        entrustCancel: function () {
            let _self = this;
            _self.entrustModal = false;
            _self.entrustForm = {
                projectId: id,
                toUserId: '',
                remark: '',
            };
        },
        getIsFinanceAdmin: function () {
            return this.isFinanceAdmin;
        },
        columnRender: function (h, param) {
            let childArray = param.row[param.column.key];
            if (childArray.length == 0) {
                return h('span');
            } else if (childArray.length == 1) {
                let value = childArray[0];
                if (param.column.colType == 'boolean') {
                    value = this.toBoolean(value);
                }else if(param.column.colType == 'money'){
                	value = common.money.formatter(value);
                }
                return h('span', value);
            } else {
                let trAarry = [];
                childArray.forEach((item, index) => {
                    if (param.column.colType == 'boolean') {
                        item = this.toBoolean(item);
                    }else if(param.column.colType == 'money'){
                    	item = common.money.formatter(item);
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
    fixed:'left',
    width: 100
}, {
    title: '项目名称',
    key: 'projectName',
    align: 'center',
    fixed:'left',
    width: 120,
    tooltip:true,
}, {
    title: '业务负责人',
    key: 'username',
    width: 100,
    fixed:'left',
    align: 'center',
}, {
    title: '追索权',
    key: 'model',
    width: 60,
    align: 'center',
}, {
    title: '债务人',
    key: 'debtor',
    width: 120,
    tooltip:true,
    align: 'center',
}, {   
	title: '债权人',
    key: 'creditor',
    width: 120,
    tooltip:true,
    align: 'center',
}, {
    title: '放款审核通过时间',
    key: 'loanAuditPassTime',
    tooltip: true,
    align: 'center',
    width: 120
}, {
    title: '应收账款（元）',
    key: 'receivable',
    align: 'center',
    width: 100,
    tooltip:true,
    colType: 'money',
    render:(h,param)=>{
    	return h('span',common.money.formatter(param.row.receivable));
    }
}, {
    title: '已投放金额（元）',
    key: 'dropAmount',
    align: 'center',
    width: 100,
    tooltip:true,
    colType: 'money',
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
    title: '回款金额（元）',
    key: 'returnAmount',
    align: 'center',
    width: 80,
    tooltip:true,
    colType: 'money',
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
    title: '保理费合计（元）',
    key: 'totalFactoringFee',
    align: 'center',
    width: 100,
    tooltip:true,
    render:(h,param)=>{
    	return h('span',common.money.formatter(param.row.totalFactoringFee));
    }
}, {
    title: '保理费分期（元）',
    key: 'factoringInstallmentAmounts',
    align: 'center',
    width: 100,
    tooltip:true,
    colType: 'money',
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
    width: 60,
    colType: 'boolean',
    render: (h, param) => {
        return vue.columnRender(h, param);
    }
}, {
    title: '操作',
    align: 'center',
    fixed:'right',
    width: 120,
    render: (h, param) => {
    	if(param.row.step>8&&param.row.step<13){
    		return  h('div', [
	        		h('Button', {
	                    props: {
	                        size: "small",
	                        type: "success",
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
	                }, '编辑'),
	                vue.getIsFinanceAdmin() ? h('Button', {
	                    props: {
	                        size: "small",
	                        type: "info",
	                        ghost: true
	                    },
	                    style: {
	                        marginRight: '5px'
	                    },
	                    on: {
	                        click: () => {
	                            vue.entrustModel(param.row.id);
	                        }
	                    }
	                }, '委托') : h('span')
	            ]
    		)
    	}else if(param.row.step==13){
    		return  h('div', [
	        		h('Button', {
	                    props: {
	                        size: "small",
	                        type: "primary",
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
	                }, '查看历史')
	            ]
			)
    	}
        
    }
}
];


