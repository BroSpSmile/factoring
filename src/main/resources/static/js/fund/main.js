/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = ['1'];

var vue = new Vue({
    el: '#fund',
    data: {
        remark: "",
        fundStatus: [],
        modal1: false,
        modal2: false,
        modal3: false,
        susProject: {},
        boundType: "INBOUND",
        users: [],
        fundTypes: [],
        itemTypes: [],
        queryParam: {
            condition: {},
            pageNum: 1,
            pageSize: 10
        },
        formTitle: "项目编辑",
        fundSteps: [],
        stepCurrent: 1,
        addForm: {
            id: 0,
            detail: {
                investment: 0,
                shareHodingRate: 0,
                preVal: 0,
                postVal: 0,
                memberA: {},
                memberB: {},
                registeredCapital: ''
            }
        },
        formInline: {
            detail: {}
        },
        inbounds: [],
        items: [],
        webItems: [{
            itemKind: "WEB",
            itemType: "PROJECT",
            itemName: "",
            itemValue: ""
        }],
        tableColumns: [],
        pageInfo: {},
        validata: {
            projectName: [
                {required: true, message: '项目名称不能为空', trigger: 'blur'}
            ],
            'detail.memberA.id': [
                {required: true, type: 'number', message: '项目成员A角不能为空', trigger: 'change'}
            ],
            'detail.memberB.id': [
                {required: true, type: 'array', min: 1, message: '项目成员B角不能为空', trigger: 'change'}
            ],
            'detail.companySortName': [
                {required: true, message: '公司简称不能为空', trigger: 'blur'}
            ],
            'detail.mainBusiness': [
                {required: true, message: '主营业务不能为空', trigger: 'blur'}
            ],
            'detail.companyFullName': [
                {required: true, message: '公司全称不能为空', trigger: 'blur'}
            ],
            'detail.controllerOwner': [
                {required: true, message: '实际控制人不能为空', trigger: 'blur'}
            ],
            'detail.registerTime': [
                {required: true, message: '注册时间不能为空', trigger: 'blur'}
            ]
        }
    },
    created: function () {
        this.initTable();
        this.initItemTypes();
        this.initDate();
        this.search();
    },
    filters: {
        timeFilter: function (value) {
            if (!value) {
                return "";
            }
            return moment(value).format('YYYY-MM-DD HH:mm');
        },

        /**
         * 翻译
         */
        toTypeName: function (value) {
            for (let index in vue.itemTypes) {
                if (vue.itemTypes[index].value == value) {
                    return vue.itemTypes[index].text;
                }
            }
            return "";
        }
    },
    methods: {
        /**
         * 初始化表格数据
         */
        initTable: function () {
            this.tableColumns = [{
                title: '项目编号',
                key: 'projectId',
                align: 'center',
                fixed: 'left',
                width: 125
            }, {
                title: '项目名称',
                key: 'projectName',
                minWidth: 120,
                tooltip: true,
                align: 'center'
            }, {
                title: '项目进度',
                key: 'detail.projectStep',
                align: 'center',
                minWidth: 120,
                render: (h, param) => {
                    return h('span', this.fliterStep(param.row.detail.projectStep))
                }
            }, {
                title: '所在地',
                tooltip: true,
                minWidth: 100,
                align: 'center',
                render: (h, param) => {
                    return h('span', param.row.detail.location)
                }
            }, {
                title: '所属行业',
                key: 'detail.industry',
                tooltip: true,
                minWidth: 100,
                align: 'center',
                render: (h, param) => {
                    return h('span', param.row.detail.industry)
                }
            }, {
                title: '主营业务',
                key: 'mainBusiness',
                minWidth: 120,
                tooltip: true,
                align: 'center'
            }, {
                title: '项目成员',
                key: 'member',
                minWidth: 100,
                align: 'center',
                render: (h, param) => {
                    let name = "";
                    if (param.row.detail.memberA != null && param.row.detail.memberA.username != null) {
                        name = param.row.detail.memberA.username;
                    }
                    if (param.row.detail.memberBs != null) {
                        let users = param.row.detail.memberBs;
                        for (let i in users) {
                            name += "," + users[i].username;
                        }
                    }
                    return h('span', name);
                }
            }, {
                title: '投资金额',
                key: 'detail.investment',
                minWidth: 100,
                align: 'center',
                render: (h, param) => {
                    return h('span', common.money.formatter(param.row.detail.investment))
                }
            }, {
                title: '投资主体',
                key: 'detail.investmentPart',
                minWidth: 100,
                align: 'center',
                render: (h, param) => {
                    return h('span', param.row.detail.investmentPart)
                }
            }, {
                title: '投后持股比例',
                key: 'detail.shareHodingRate',
                width: 80,
                align: 'center',
                render: (h, param) => {
                    return h('span', param.row.detail.shareHodingRate + "%")
                }
            }, {
                title: '投前估值',
                key: 'detail.preVal',
                width: 100,
                align: 'center',
                render: (h, param) => {
                    return h('span', common.money.formatter(param.row.detail.preVal))
                }
            }, {
                title: '投后估值',
                key: 'detail.postVal',
                width: 100,
                align: 'center',
                render: (h, param) => {
                    return h('span', common.money.formatter(param.row.detail.postVal))
                }
            }, {
                title: '出资时间',
                key: 'detail.investemntTime',
                align: 'center',
                width: 100,
                render: (h, param) => {
                    return h('span', param.row.detail.investemntTime ? moment(param.row.detail.investemntTime).format('YYYY-MM-DD') : "")
                }
            }, {
                title: '操作',
                fixed: 'right',
                align: 'center',
                width: 150,
                render: (h, param) => {
                    return h('div', [
                        param.row.detail.projectStep != 'POST_INVESTMENT' && param.row.detail.projectStep != 'OUT' ? h('Button', {
                            props: {
                                size: "small",
                                type: "warning",
                                ghost: true

                            },
                            on: {
                                click: () => {
                                    this.toMenu(param.row, "项目编辑");
                                }
                            }
                        }, '编辑') : h('span'),
                        param.row.detail.projectStep == 'INITIAL_CONTACT' || param.row.detail.projectStep == 'SIGN_CONFIDENTIALITY' || param.row.detail.projectStep == 'APPROVAL' || param.row.detail.projectStep == 'INITIAL_TUNING' || param.row.detail.projectStep == 'DEEP_TUNING' || param.row.detail.projectStep == 'INITIAL_OPINION' ? h('Button', {
                            props: {
                                size: "small",
                                type: "primary",
                                ghost: true

                            },
                            on: {
                                click: () => {
                                    this.modal3 = true;
                                    this.susProject = param.row;
                                }
                            }
                        }, '暂停') : h('span'),
                        param.row.detail.projectStep == 'SUSPEND' ? h('Button', {
                            props: {
                                size: "small",
                                type: "success",
                                ghost: true

                            },
                            on: {
                                click: () => {
                                    this.restart(param.row);
                                }
                            }
                        }, '重启') : h('span'),
                        param.row.detail.projectStep == 'INITIAL_CONTACT' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("fundOpinion", '初步意见', param.row.id);
                                }
                            }
                        }, '初步意见') : h('span'),
                        param.row.detail.projectStep == 'SIGN_CONFIDENTIALITY' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("initContact", '签署保密协议', param.row.id);
                                }
                            }
                        }, '签署保密协议') : h('span'),
                        param.row.detail.projectStep == 'INITIAL_TUNING' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("initialTuning", "初步尽调", param.row.id);
                                }
                            }
                        }, '初步尽调') : h('span'),
                        param.row.detail.projectStep == 'APPROVAL' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("meeting", "项目立项", param.row.id);
                                }
                            }
                        }, "项目立项") : h('span'),
                        param.row.detail.projectStep == 'DEEP_TUNING' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("deepTuning", "深入尽调", param.row.id);
                                }
                            }
                        }, '深入尽调') : h('span'),
                        param.row.detail.projectStep == 'PARTMENT_AUDIT' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.commitAudit(param.row);
                                }
                            }
                        }, '风控审核') : h('span'),
                        param.row.detail.projectStep == 'SASAC_APPROVAL' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("sasacAudit", "国资委审批", param.row.id);
                                }
                            }
                        }, '国资委审批') : h('span'),
                        param.row.detail.projectStep == 'GOV_APPROVAL' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("sasacAudit", "区政府审批", param.row.id);
                                }
                            }
                        }, '区政府审批') : h('span'),
                        param.row.detail.projectStep == 'CONTRACT_SIGN' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("fundContract", "合同上传", param.row.id);
                                }
                            }
                        }, '合同上传') : h('span'),
                        param.row.detail.projectStep == 'CONTRACT_SIGNED' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("fundContract", "盖章合同上传", param.row.id);
                                }
                            }
                        }, '盖章合同上传') : h('span'),
                        param.row.detail.projectStep == 'PAYMENT' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("payment", "上传付款凭证", param.row.id);
                                }
                            }
                        }, '上传付款凭证') : h('span'),
                        param.row.detail.projectStep == 'FILE' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("filingApply", "项目归档", param.row.id);
                                }
                            }
                        }, '项目归档') : h('span'),
                        param.row.detail.projectStep == 'POST_INVESTMENT' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toMenu(param.row, "投后管理");
                                }
                            }
                        }, '投后管理') : h('span'),
                        param.row.detail.projectStep == 'OUT' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toMenu(param.row, "");
                                }
                            }
                        }, '查看') : h('span'),
                    ])
                }
            }]
        },

        /**
         * 初始化附件类型
         */
        initItemTypes: function () {
            let _self = this;
            this.$http.get("/combo/itemTypes").then(function (response) {
                _self.itemTypes = response.data;
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/inbound").then(function (response) {
                _self.inbounds = response.data;
            }, function (error) {
                console.error(error);
            });
        },

        /**
         * 跳转菜单
         */
        toMenu: function (project, title) {
            this.formTitle = title;
            this.addForm = JSON.parse(JSON.stringify(project));
            if (null == this.addForm.detail.memberA) {
                this.addForm.detail.memberA = {};
            }
            if (null == this.addForm.detail.memberBArr) {
                this.addForm.detail.memberBArr = [];
            }
            this.fileList = [];
            this.getItems(project.id);
            this.webItems = [{
                itemKind: "WEB",
                itemType: "PROJECT",
                itemName: "",
                itemValue: ""
            }];
            this.getSteps(project.detail.investment,project.detail.projectStep);

         //   console.log(this.addForm);
            this.modal1 = true;
        },

        /**
         * 获取项目附件
         * @param id
         */
        getItems: function (id) {
            let _self = this;
            this.items = [];
            this.$http.post("/project/items/" + id).then(function (response) {
                let items = response.data;
                for (let index in items) {
                    let has = false;
                    for (let j in _self.items) {
                        if (_self.items[j].key == items[index].itemType) {
                            _self.items[j].value.push(items[index]);
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        let item = {key: items[index].itemType, value: []};
                        item.value.push(items[index]);
                        _self.items.push(item);
                    }
                }
            }, function (error) {
                console.error(error);
            })
        },

        /**
         * 获取步骤
         * @param investment
         */
        getSteps: function (investment,step) {
            let _self = this;
            this.$http.get("/combo/fundStatus/" + investment).then(function (response) {
                _self.fundSteps = response.data;
                _self.stepCurrent = -1;
                for (let index in this.fundSteps) {
                    if (_self.fundSteps[index].code == step) {
                        if(investment<=15000000.00 &&  _self.fundSteps[index].index>9){
                            _self.stepCurrent = _self.fundSteps[index].index -3 ;
                        }else if(investment>15000000.00&&investment<=50000000.00 & _self.fundSteps[index].index>9){
                            _self.stepCurrent = _self.fundSteps[index].index -2 ;
                        }else{
                            _self.stepCurrent = _self.fundSteps[index].index -1 ;
                        }

                    }
                }
                console.log(_self.stepCurrent);
            }, function (error) {
                console.error(error);
            });
        },

        toEdit:function(current){
            if(current == 'INITIAL_OPINION'){
                this.toNewTab("fundOpinion", '初步意见', this.addForm.id);
            }
            if(current == 'SIGN_CONFIDENTIALITY'){
                this.toNewTab("initContact", '保密协议', this.addForm.id);
            }
            if(current == 'INITIAL_TUNING'){
                this.toNewTab("initialTuning", '初步尽调', this.addForm.id);
            }
            if(current == 'DEEP_TUNING'){
                this.toNewTab("deepTuning", '深入尽调', this.addForm.id);
            }
            if(current == 'CONTRACT_SIGN'){
                this.toNewTab("fundContract", '合同上传', this.addForm.id);
            }
            if(current == 'CONTRACT_SIGNED'){
                this.toNewTab("fundContract", '盖章合同上传', this.addForm.id);
            }
            if(current == 'PAYMENT'){
                this.toNewTab("payment", '付款凭证', this.addForm.id);
            }

        },
        /**
         * 打开新标签
         */
        toNewTab: function (menu, menuName, projectId) {
            if (projectId) {
                parent.window.menu.createNew({
                    name: menuName,
                    url: menu + "?id=" + projectId,
                    id: menu + "?id=" + projectId
                });
            } else {
                parent.window.menu.createNew({
                    name: menuName,
                    url: menu,
                    id: menu
                });
            }
        },


        suspend: function () {
            let _self = this;
            this.susProject.remark = this.remark;
            this.$http.post("/fund/suspend", JSON.stringify(this.susProject)).then(function (response) {
                let result = response.data;
                if (result.success) {
                    _self.$Message.info("暂停成功");
                    _self.modal3 = false;
                    _self.search();
                } else {
                    _self.$Message.error(result.errorMessage);
                }

            }, function (error) {
                console.error(error);
            });
        },

        restart: function (project) {
            let _self = this;
            this.$Modal.confirm({
                title: "是否重新启动项目",
                onOk: function (event) {
                    this.$http.post("/fund/restart", JSON.stringify(project)).then(function (response) {
                        let result = response.data;
                        if (result.success) {
                            _self.$Message.info("项目重启成功");
                            _self.search();
                        } else {
                            _self.$Message.error(result.errorMessage);
                        }
                    }, function (error) {
                        console.error(error);
                    });
                },
                onCancel: function (event) {

                }
            });

        },

        /**
         * 提交审核
         * @param project
         */
        commitAudit: function (project) {
            let _self = this;
            this.$Modal.confirm({
                title: "是否提交风控审核",
                onOk: function (event) {
                    this.$http.post("/innerAudit", project).then(function (response) {
                        let result = response.data;
                        if (result.success) {
                            _self.$Message.info("提交审核成功");
                            _self.search();
                        } else {
                            _self.$Message.error(result.errorMessage);
                        }
                    }, function (error) {
                        console.error(error);
                    })

                },
                onCancel: function (event) {

                }
            });
        },

        fliterStep: function (step) {
            for (let index in this.fundStatus) {
                if (this.fundStatus[index].value == step) {
                    return this.fundStatus[index].text;
                }
            }
            return "";
        },

        /**
         * 初始化数据
         */
        initDate: function () {
            var _self = this;
            this.$http.get("/combo/fundStatus").then(function (response) {
                _self.fundStatus = response.data;
            }, function (error) {
            });
            this.$http.get("/combo/users").then(function (response) {
                _self.users = response.data;
            }, function (error) {
            });
            this.$http.get("/combo/fundType").then(function (response) {
                _self.fundTypes = response.data;
            }, function (error) {
            });
        },

        /**
         * 搜索
         */
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
         * 下载
         */
        download: function () {
            this.queryParam.pageNum = 1;
            let self = this;
            self.queryParam.condition = self.formInline;
            if (self.queryParam.condition.stateDate) {
                self.queryParam.condition.endDate = self.queryParam.condition.stateDate[1];
                self.queryParam.condition.stateDate = self.queryParam.condition.stateDate[0];
            }
            window.open("/fund/download?query=" + encodeURI(JSON.stringify(this.queryParam)));
        },

        /** 分页查询 */
        query: function () {
            let self = this;
            self.queryParam.condition = self.formInline;
            if (self.queryParam.condition.stateDate) {
                self.queryParam.condition.endDate = self.queryParam.condition.stateDate[1];
                self.queryParam.condition.stateDate = self.queryParam.condition.stateDate[0];
            }
            this.$http.post("/fund/query", self.queryParam).then(
                function (response) {
                    let data = response.data;
                    for (let index in data.list) {
                        data.list[index].detail.memberBArr = [];
                        let users = data.list[index].detail.memberBStr.split(",");
                        for (let i in users) {
                            data.list[index].detail.memberBArr.push(parseInt(users[i]));
                        }
                        data.list[index].mainBusiness = data.list[index].detail.mainBusiness;
                    }
                    this.pageInfo = data;
                }, function (error) {
                    self.$Message.error(error.data.message);
                })
        },


        /**
         *
         */
        open: function () {
            this.addForm = {
                detail: {
                    investment: 0,
                    shareHodingRate: 0,
                    preVal: 0,
                    postVal: 0,
                    memberA: {},
                    memberB: {},
                    registeredCapital: 0
                },
                items: []
            };
            this.items = [];
            this.webItems = [{
                itemKind: "WEB",
                itemType: "PROJECT",
                itemName: "",
                itemValue: ""
            }];
            this.fileList = [];
            this.modal1 = true;
        },

        /**
         * 查询公司信息
         */
        queryCompany: function () {
            let _self = this;
            this.$http.get("/fund/company/" + this.addForm.detail.companyFullName).then(function (response) {
                let company = response.data;
                if (company) {
                    _self.addForm.detail.companyFullName = company.name;
                    _self.addForm.detail.controllerOwner = company.legalPersonName;
                    _self.addForm.detail.registeredCapital = company.regCapital;
                    _self.addForm.detail.address = company.regLocation;
                    _self.addForm.detail.industry = company.industry;
                    _self.addForm.detail.orgNumber = company.orgNumber;
                    _self.addForm.detail.creditCode = company.creditCode;
                    _self.addForm.detail.updatetime = company.updatetime;
                    _self.addForm.detail.taxNumber = company.taxNumber;
                    _self.addForm.detail.phoneNumber = company.phoneNumber;
                    _self.addForm.detail.registerTime = company.estiblishTime;
                }
            }, function (error) {
                console.error(error);
            });
        },

        /**
         * 重置
         */
        reset: function () {
            this.formInline = {};
            this.$refs['searchForm'].resetFields();
        },

        save: function () {
            let _self = this;
            this.$refs['entityDataForm'].validate((valid) => {
                if (!valid) {
                    _self.$Message.error('校验失败,请完善信息!');
                    return false;
                } else {
                    _self.commit();
                }
            });
        },

        /**
         * 项目退出
         */
        projectOut: function () {
            let _self = this;
            this.addForm.detail.projectStep = "OUT";

            this.$http.put("/fund", _self.addForm).then(function (response) {
                let result = response.data;
                if (result.success) {
                    _self.$Message.info({
                        content: "项目退出成功",
                        onClose: function () {
                            _self.modal2 = false;
                            _self.modal1 = false;
                            _self.search();
                        }
                    });
                } else {
                    _self.$Message.error(result.errorMessage);
                }
            }, function (error) {
                console.error(error);
            })
        },

        /**
         *
         */
        commit: function () {
            let self = this;
            this.addForm.items = [];
            for (let index in this.fileList) {
                let item = {
                    itemName: this.fileList[index].name,
                    itemValue: this.fileList[index].response.data.fileId
                }
                this.addForm.items.push(item);
            }
            for (let index in this.webItems) {
                if (this.webItems[index].itemValue) {
                    this.addForm.items.push(this.webItems[index]);
                }
            }
            this.addForm.detail.memberBs = [];
            for (let index in this.addForm.detail.memberBArr) {
                this.addForm.detail.memberBs.push({id: this.addForm.detail.memberBArr[index]});
            }

            if (!this.addForm.projectId) {
                this.$http.post("/fund", this.addForm).then(function (response) {
                    this.$Spin.hide();
                    if (response.data.success) {
                        self.$Message.info({
                            content: "保存成功",
                            onClose: function () {
                                self.modal1 = false;
                                self.search();
                            }
                        });
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    this.$Spin.hide();
                    self.$Message.error(error.data.message);
                });
            } else {
                this.$http.put("/fund", this.addForm).then(function (response) {
                    this.$Spin.hide();
                    if (response.data.success) {
                        self.$Message.info({
                            content: "保存成功",
                            onClose: function () {
                                self.modal1 = false;
                                self.search();
                            }
                        });
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    this.$Spin.hide();
                    self.$Message.error(error.data.message);
                });
            }
        },

        /**
         * 取消保存
         */
        cancel: function () {
            this.modal1 = false;
            if (this.addForm.projectId == '') {
                this.$refs['entityDataForm'].resetFields();
            }
        },

        /**
         * 文件上传成功
         */
        uploadSuccess: function (response, file, fileList) {
            this.fileList = fileList;
        },

        /**
         * 文件上传失败
         */
        uploadError: function (error, file, fileList) {
            this.fileList = fileList;
        },

        removeFile: function (file, fileList) {
            this.fileList = fileList;
            let fileId = file.response.data.fileId;
            let self = this;
            this.$http.delete("/file/" + fileId).then(function (response) {
                if (response.data.success) {
                    self.$Message.info("删除成功");
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error.data.errorMessage);
            })

        },

        /**
         * formatter
         */
        formatter: function (value) {
            value = value + '';
            //处理小数部分
            if (value.indexOf(".") != -1) {
                let values = value.split(".");
                let intSum = values[0].replace(/\B(?=(?:\d{3})+$)/g, ',');
                let floatSum = values[1];
                return '￥' + intSum +"."+ floatSum;
            } else {
                var intSum = value.replace(/\B(?=(?:\d{3})+$)/g, ',');
                return '￥' + intSum;
            }
        },

        parser: function (value) {
            value = "" + value;
            return value.replace(/￥s?|(,*)/g, '');
        },

        /** 添加 */
        add: function () {
            this.webItems.push({
                itemKind: "WEB",
                itemType: "PROJECT",
                itemName: "",
                itemValue: ""
            });
        },

        /**
         * 下载文件
         */
        downloadItem: function (item) {
            window.open("/file?fileId=" + item.itemValue + "&fileName=" + encodeURI(item.itemName));
        },

        /**
         * 打开文件
         */
        openItem: function (item) {
            window.open(item.itemValue);
        },

        /**
         * 移除
         */
        remove: function (index) {
            this.webItems.splice(index, 1);
        },
    }
});


