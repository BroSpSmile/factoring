/**
 * 菜单信息
 */
common.pageName = "filingProject";
common.openName = ['5'];

var vue = new Vue({
    el: '#filingProject',
    data: {
        formInline: {
            projectId: null,
            projectName: null,
            person: null,
            progress: null,
            progresses: null
        },
        addForm: {
            projectId: "",
            projectName: "",
            person: ""
        },
        queryParam: {
            condition: {},
            pageNum: 1,
            pageSize: 10
        },
        pageInfo: {},
        modal1: false,
        statusItems: []
    },
    created: function () {
        this.initDate();
    },
    methods: {
        /**
         * 初始化数据
         */
        initDate: function () {
            var _self = this;
            this.$http.get("/combo/filingProgress").then(function (response) {
                _self.statusItems = response.data;
                this.search();
            }, function (error) {
                console.error(error);
            })
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

        /**
         * 设置查询进度条件，all
         */
        setProgresses: function (value) {
            let _self = this;
            var progresses = [];

            if (!_self.formInline.progress) {
                for (var index in this.statusItems) {
                    if (this.statusItems[index].value) {
                        progresses.push(this.statusItems[index].value)
                    }
                }
                _self.formInline.progresses = progresses;
            }
        },

        /** 分页查询 */
        query: function () {
            this.setProgresses();
            let self = this;
            self.queryParam.condition = self.formInline;
            this.$http.post("/approval/query", self.queryParam).then(
                function (response) {
                    self.pageInfo = response.data;
                }, function (error) {
                    self.$Message.error(error.data.message);
                })
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
         * 归档申请
         */
        apply: function (projectId) {
            window.open("filingApply?id=" + projectId, "_self");
        },

        /**
         * 归档审批
         */
        audit: function (projectId) {
            window.open("filingAudit?id=" + projectId, "_self");
        },
        /**
         * 归档审批查看
         */
        view: function (projectId) {
            window.open("filingAudit?view=true&id=" + projectId, "_self");
        }
    }
});

vue.tableColumns = [
    {
        title: '项目编号',
        key: 'projectId',
        align: 'center'
    }, {
        title: '项目名称',
        key: 'projectName',
        align: 'center'
    }, {
        title: '项目负责人',
        key: 'person',
        align: 'center'
    }, {
        title: '当前进度',
        key: 'progress',
        align: 'center',
        render: (h, param) => {
            return h('span', vue.getProgress(param.row.progress));
        }
    }, {
        title: '操作',
        align: 'center',
        render: (h, param) => {
            if (param.row.progress == 'TOBEFILED') {
                return h('div', [
                    h('Button', {
                        props: {
                            size: "small",
                            type: "success"
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                vue.apply(param.row.projectId);
                            }
                        }
                    }, '归档申请')
                ])
            } else if (param.row.progress == 'FILE') {
                return h('div', [
                    h('Button', {
                        props: {
                            size: "small",
                            type: "warning"
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                vue.audit(param.row.projectId);
                            }
                        }
                    }, '归档审核')]);
            } else if (param.row.progress == 'FILEAUDIT') {
                return h('div', [
                    h('Button', {
                        props: {
                            size: "small",
                            type: "error"
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                vue.audit(param.row.projectId);
                            }
                        }
                    }, '归档审核')]);
            } else if (param.row.progress == 'FILECOMPLETE') {
                return h('div', [
                    h('Button', {
                        props: {
                            size: "small",
                            type: "info"
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                                vue.view(param.row.projectId);
                            }
                        }
                    }, '审核详情')]);
            } else {
                return h('div', [
                    h('span')]);
            }
        }
    }
];


