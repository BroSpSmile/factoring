/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = ['1'];

var vue = new Vue({
    el: '#fund',
    data: {
        fundStatus: [],
        modal1: false,
        users: [],
        itemTypes:[],
        queryParam: {
            condition: {},
            pageNum: 1,
            pageSize: 10
        },
        formTitle: "项目编辑",
        addForm: {
            id: 0,
            detail: {
                investment: 0,
                shareHodingRate:0,
                preVal:0,
                postVal:0,
                memberA: {},
                memberB: {},
                registeredCapital: ''
            }
        },
        formInline: {
            detail: {}
        },
        items: [],
        webItems:[{
            itemKind:"WEB",
            itemType:"PROJECT",
            itemName:"",
            itemValue:""
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
                {required: true, type: 'number', message: '项目成员B角不能为空', trigger: 'change'}
            ],
            'detail.companySortName': [
                {required: true, message: '公司简称不能为空', trigger: 'blur'}
            ],
            'detail.companyFullName': [
                {required: true, message: '公司全称不能为空', trigger: 'blur'}
            ],
            'detail.controllerOwner': [
                {required: true, message: '实际控制人不能为空', trigger: 'blur'}
            ],
            'detail.chairman': [
                {required: true, message: '董事长不能为空', trigger: 'blur'}
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
        toTypeName:function(value){
            for(let index in vue.itemTypes){
                if(vue.itemTypes[index].value == value){
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
                    console.log();
                    if (param.row.detail.memberA != null && param.row.detail.memberA.username != null) {
                        name = param.row.detail.memberA.username;
                    }
                    if (param.row.detail.memberB != null && param.row.detail.memberB.username != null) {
                        name += "," + param.row.detail.memberB.username;
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
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
                                    this.toMenu(param.row, "项目编辑");
                                }
                            }
                        }, '编辑') : h('span'),
                        param.row.detail.projectStep == 'INITIAL_CONTACT' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("initContact", '签署保密协议', param.row.id);
                                }
                            }
                        }, '签署保密协议') : h('span'),
                        param.row.detail.projectStep == 'SIGN_CONFIDENTIALITY' ? h('Button', {
                            props: {size: 'small', type: "info", ghost: true}, on: {
                                click: () => {
                                    this.toNewTab("initialTuning", "初步尽调", param.row.id);
                                }
                            }
                        }, '初步尽调') : h('span'),
                        param.row.detail.projectStep == 'INITIAL_TUNING' ? h('Button', {
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
        initItemTypes:function(){
            let _self = this;
            this.$http.get("/combo/itemTypes").then(function(response){
                _self.itemTypes = response.data;
            },function(error){
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
            if (null == this.addForm.detail.memberB) {
                this.addForm.detail.memberB = {};
            }
            this.fileList = [];
            this.getItems(project.id);
            this.webItems = [{
                itemKind:"WEB",
                itemType:"PROJECT",
                itemName:"",
                itemValue:""
            }];
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
                for(let index in items){
                    let has = false;
                    for(let j in _self.items){
                        if(_self.items[j].key == items[index].itemType){
                            _self.items[j].value.push(items[index]);
                            has = true;
                            break;
                        }
                    }
                    if(!has){
                        let item = {key:items[index].itemType,value:[]};
                        item.value.push(items[index]);
                        _self.items.push(item);
                    }
                }
            }, function (error) {
                console.error(error);
            })
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
                    for(let index in data.list){
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
                    shareHodingRate:0,
                    preVal:0,
                    postVal:0,
                    memberA: {},
                    memberB: {},
                    registeredCapital: 0
                },
                items: []
            };
            this.items = [];
            this.webItems = [{
                itemKind:"WEB",
                itemType:"PROJECT",
                itemName:"",
                itemValue:""
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
                if(company){
                    _self.addForm.detail.companyFullName = company.name;
                    _self.addForm.detail.controllerOwner = company.legalPersonName;
                    _self.addForm.detail.registeredCapital = company.regCapital;
                    _self.addForm.detail.address = company.regLocation;
                    _self.addForm.detail.industry = company.industry;
                    _self.addForm.detail.mainBusiness = company.businessScope;
                    _self.addForm.detail.orgNumber = company.orgNumber;
                    _self.addForm.detail.creditCode = company.creditCode;
                    _self.addForm.detail.updatetime = company.updatetime;
                    _self.addForm.detail.taxNumber = company.taxNumber;
                    _self.addForm.detail.phoneNumber = company.phoneNumber;
                    for(let i in company.staffList){
                        if(company.staffList[i].staffTypeName == "董事长"){
                            _self.addForm.detail.chairman = company.staffList[i].name;
                        }
                    }
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
            this.$Modal.confirm({
                title: "是否完结项目退出",
                onOk: function (event) {
                    _self.addForm.detail.projectStep = "OUT";
                    this.$http.put("/fund", _self.addForm).then(function (response) {
                        let result = response.data;
                        if (result.success) {
                            _self.$Message.info({
                                content: "项目退出成功",
                                onClose: function () {
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
                onCancel: function (event) {

                }
            });
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
            for(let index in this.webItems){
                this.addForm.items.push(this.webItems[index]);
            }
            if (!this.addForm.projectId) {
                console.log(this.addForm);
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
            var intSum = value.replace(/\B(?=(?:\d{3})+$)/g, ',');
            return '￥' + intSum;
        },

        parser: function (value) {
            value = "" + value;
            return value.replace(/￥s?|(,*)/g, '')
        },

        /** 添加 */
        add:function(){
            this.webItems.push({
                itemKind:"WEB",
                itemType:"PROJECT",
                itemName:"",
                itemValue:""
            });
        },

        /**
         * 下载文件
         */
        downloadItem:function(item){
            window.open("/file?fileId="+item.itemValue+"&fileName="+encodeURI(item.itemName));
        },

        /**
         * 打开文件
         */
        openItem:function(item){
            window.open(item.itemValue);
        },

        /**
         * 移除
         */
        remove:function(index){
            this.webItems.splice(index,1);
        },
    }
});


