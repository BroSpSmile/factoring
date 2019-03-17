/**
 * 菜单信息
 */
common.pageName = "financeManage";
common.openName = [ 'financeManage' ];

var vue = new Vue({
    el : '#financeManage',
    data : {
        formInline : {
            projectId : null,
            projectName : null,
            person : null,
            progress : null
        },
        addForm : {

        },
        queryParam : {
            condition : {},
            pageNum : 1,
            pageSize : 10
        },
        pageInfo:{},
        modal1 : false,
        statusItems : [],
        project : {
            projectId : "",
            detail : {
                loanInstallments : []
            }
        },
        loanInstallment : {
            amount : 0,
            detail : {
                id : 0
            },
            installmentDate : ""
        }
    },
    created : function() {
        this.initDate();
        this.query();
    },
    methods : {
        /**
         * 初始化数据
         */
        initDate : function() {
            var _self = this;
            this.$http.get("/combo/progress").then(function(response) {
                _self.statusItems = response.data;
            }, function(error) {
                console.error(error);
            });
            this.$http.get("/combo/projectModel").then(function(response) {
                _self.models = response.data;
            }, function(error) {
                console.error(error);
            });
            this.$http.get("/combo/steps").then(function(response) {
                _self.steps = response.data;
            }, function(error) {
                console.error(error);
            });
        },

        /**
         * 翻译
         */
        toModelName:function(value){
            for(let index in this.models){
                if(this.models[index].value == value){
                    return this.models[index].text;
                }
            }
            return "";
        },

        /**
         * 翻译
         */
        toStepName:function(value){
            for(let index in this.steps){
                if(this.steps[index].value == value){
                    return this.steps[index].text;
                }
            }
            return "";
        },

        toBoolean:function(value){
            if(value){
                return "是";
            }
            return "否";
        },

        /**
         * 状态翻译
         */
        getProgress:function(value){
            for(var index in this.statusItems){
                if(value==this.statusItems[index].value){
                    return this.statusItems[index].text;
                }
            }
            return "";
        },

        /** 分页查询 */
        query : function() {
            let self = this;
            self.queryParam.condition = self.formInline;
            this.$http.post("/project/query", self.queryParam).then(
                function(response) {
                    let data = response.data;
                    console.log(data)
                    for(let index in data.list){
                        try{
                            data.list[index].creditor = data.list[index].detail.creditor;
                            data.list[index].debtor = data.list[index].detail.debtor;
                        }catch(e){}
                    }
                    self.pageInfo = data;
                }, function(error) {
                    self.$Message.error(error.data.message);
                })
        },

        /** 搜索 */
        search:function(){
            this.queryParam.pageNum = 1;
            this.query();
        },

        /** 分页 */
        pageChange:function(page){
            this.queryParam.pageNum = page;
            this.query();
        },

        /**
         * 重置
         */
        reset:function(){
            this.$refs['searchForm'].resetFields();
        },
        /** 保存放款信息 */
        saveLoanInstallment : function() {
            let self = this;
            this.loanInstallment.detail.id = this.project.detail.id;
            this.$http.post("/finance/saveLoanInstallment", this.loanInstallment).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "保存成功",
                        onClose : function() {
                            self.project.detail.loanInstallments.push(self.loanInstallment);
                            console.log(self.project.detail)
                            self.loanInstallment = {
                                amount : 0,
                                detail : {
                                    id : 0
                                },
                                installmentDate : ""
                            }
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function(error) {
                self.$Message.error(error.data.message);
            });
        },

        /**
         * 取消
         */
        cancel : function() {
            this.modal1 = false;
        },

        /**
         * 跳转菜单
         */
        operate : function(project) {
            this.modal1 = true;
            this.project = project;
        },
        /**
         * 文件上传成功
         */
        uploadSuccess: function (response, file, fileList) {
            // this.fileList = fileList;
            // console.log(fileList);
        },
        /**
         * 文件上传失败
         */
        uploadError: function (error, file, fileList) {
            // this.fileList = fileList;
            // console.log(error);
        },
        removeFile: function (file, fileList) {
            this.fileList = fileList;
            console.log(file);
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
        genFileInfo: function () {
            for (let index in this.fileList) {
                let item = {
                    attachName: this.fileList[index].name,
                    fileId: this.fileList[index].response.data.fileId
                };
                this.addForm.attachList.push(item);
            }
            return true;
        }
    }
});

vue.tableColumns=[{
    title: '项目编号',
    key: 'projectId',
    align: 'center',
    width:125
},{
    title: '项目名称',
    key: 'projectName',
    width:125,
    tooltip:true,
    align: 'center'
},{
    title: '让与人',
    key: 'creditor',
    tooltip:true,
    align: 'center'
},{
    title: '债务人',
    key: 'debtor',
    tooltip:true,
    align: 'center'
},{
    title: '追索权',
    key: 'projectName',
    align: 'center',
    render:(h,param)=>{
        return h('span',vue.toModelName(param.row.model))
    }
},{
    title: '应收账款受让款(万元)',
    key: 'projectName',
    align: 'center',
    render:(h,param)=>{
        return h('span',param.row.detail.assignee)
    }
},{
    title: '应收账款(万元)',
    key: 'projectName',
    align: 'center',
    render:(h,param)=>{
        return h('span',param.row.detail.receivable)
    }
},{
    title: '转让期限年',
    key: 'projectName',
    align: 'center',
    render:(h,param)=>{
        return h('span',param.row.detail.duration)
    }
},{
    title: '项目负责人',
    key: 'user',
    align: 'center',
    render:(h,param)=>{
        return h('span',param.row.user.username)
    }
},{
    title: '当前进度',
    key: 'progress',
    align: 'center',
    render:(h,param)=>{
        return h('span',vue.getProgress(param.row.progress));
    }
},{
    title: '操作',
    align: 'center',
    render:(h,param)=>{
        return h('div', [
                h('span'),
                h('Button', {
                    props: {
                        size: "small",
                        type: "warning",
                        ghost:true
                    },
                    style: {
                        marginRight: '5px'
                    },
                    on: {
                        click: () => {
                            vue.operate(param.row);
                        }
                    }
                }, '登记')
            ]
        )
    }
}
];


