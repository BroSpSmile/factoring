/**
 * 菜单信息
 */
common.pageName = "financeManage";
common.openName = [ 'financeManage' ];

var vue = new Vue({
    el : '#financeOperation',
    data : {
        projectUrl: 'financeManage',
        project:{
        },
        statusItems : [],
        steps:[],
        models:[],
        queryParam: {
            condition: {},
            pageNum: 1,
            pageSize: 10
        },
        formInline: {
            id:-1,
            projectId: null,
            projectName: null,
            person: null,
            progress: null
        },
    },
    created : function() {
        this.formInline.id = document.getElementById("projectId").value;
        this.queryParam.condition = this.formInline;
        this.initDate();
        this.query();
    },
    methods : {
        /**
         * 初始化数据
         */
        initDate : function() {
            let _self = this;
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

        /** 分页查询 */
        query : function() {
            let self = this;
            self.queryParam.condition = self.formInline;
            this.$http.post("/project/query", self.queryParam).then(
                function(response) {
                    let data = response.data;
                    self.project=data.list[0];
                }, function(error) {
                    self.$Message.error(error.data.message);
                })
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

        /** 保存项目 */
        saveProject : function() {
            let self = this;
            if(this.addForm.id==null||this.addForm.id==""){
                this.$http.post("/approval", this.addForm).then(function(response) {
                    if (response.data.success) {
                        self.$Message.info({
                            content : "保存成功",
                            onClose : function() {
                                self.query();
                                self.cancel();
                            }
                        });
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function(error) {
                    self.$Message.error(error.data.message);
                });
            }else{
                this.$http.put("/approval", this.addForm).then(function(response) {
                    if (response.data.success) {
                        self.$Message.info({
                            content : "更新成功",
                            onClose : function() {
                                self.query();
                                self.cancel();
                            }
                        });
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function(error) {
                    self.$Message.error(error.data.message);
                });
            }

        },

        cancel: function () {
            window.open(this.projectUrl, "_self");
        },
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
								vue.operate(param.row.id);
							}
						}
					}, '登记')
				]
			)
		}
	}
];


