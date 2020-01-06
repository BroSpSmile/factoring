/**
 * 菜单信息
 */
common.pageName = ("0" == document.getElementById("type").value ? 'filingProject' : "filingProjectAudit");
common.openName = ['5'];

var vue = new Vue({
    el: '#filingApply',
    data: {
        formInline: {
            type: []
        },
        project:{},
        contract:{
        	signList:[],
        	project:{
        		id:0
        	}
        },
        fileList:[],
        signList:[],
        isInitFileRow: false,
        isEdit:false,
        projectUrl: 'filingProject?type=0'
    },
    created: function () {
        this.contract.project.id = document.getElementById("projectId").value;
        this.initDate();
        this.getProject(this.contract.project.id);
    },
    filters:{
        /**
         * 清单类别
         */
        getCategoryDesc : function(value){
            if(value === 1) {
                return "立项材料";
            } else if(value === 2) {
                return "尽调报告";
            } else if(value === 3) {
                return "风控意见材料";
            } else if(value === 4) {
                return "投决会材料";
            } else if(value === 5) {
                return "协议签署及打款材料";
            } else if(value === 6) {
                return "三方尽调材料";
            }else if(value === 7) {
                return "其他";
            }
            return "";
        },
        /**
         * 原件/复印件
         */
        getIsOriginalCopyDesc : function(value){
            if(value === 1) {
                return "原件";
            } else if(value === 0) {
                return "复印件";
            }
            return "-";
        }
    },
    methods: {
        /**
		 * 初始化数据
		 */
        initDate: function () {
            var _self = this;
//            this.$http.get("/filingAudit/" + this.filingInfo.project).then(function (response) {
//                if (response.data.success) {
//                    if (null != response.data.data && 'null' != response.data.data) {
//                        _self.filingInfo = response.data.data;
//                        if (_self.filingInfo.items.length > 0) {
//                            _self.isInitFileRow = true;
//                        }
//                    }
//                } else {
//                    self.$Message.error(response.data.errorMessage);
//                }
//
//            }, function (error) {
//                console.error(error);
//            });
            if(this.contract.project.id){
            	 this.$http.get("contractInfo/project/"+this.contract.project.id).then(function(response){
        			 _self.signList = response.data;
        			 _self.contract.signList = [];
        			 for(let index in _self.signList){
        				 if(_self.signList[index].filingStatus >=2){
                             _self.signList[index].getReady = true;
        					 // _self.contract.signList.push(_self.signList[index].serialNo);
        				 }
        			 }
            	 },function(error){
            		 console.error(error);
            	 })
            }
        },

        /**
         * 获取项目信息
         */
        getProject:function(id){
            let _self = this;
            this.$http.get("/project/"+id).then(function(response){
                _self.project = response.data;
            },function(error){
                console.error(error);
            })
        },

        /**
         * 清单项验证
         * commitFla标志位判断是保存操作还是提交操作，如果是保存操作只针对新增行进行验证，如果是提交操作，则验证所有行
         * @param commitFlag
         * @returns {boolean}
         */
        validate : function (commitFlag) {
            let self = this;
            //录入项验证
            for (let index in this.signList) {
                if (this.signList[index].type === 'add' || commitFlag) {
                    if(this.signList[index].category === undefined || this.signList[index].category === null) {
                        self.$Message.error('请选择文件类型');
                        return false;
                    }
                    if(this.signList[index].signListName === '') {
                        self.$Message.error('文件名称必须输入');
                        return false;
                    }
                    if(this.signList[index].isOriginalCopy === undefined || this.signList[index].isOriginalCopy === null) {
                        self.$Message.error('请选择原件/复印件');
                        return false;
                    }
                    if(this.signList[index].copies === null) {
                        self.$Message.error('文件份数必须输入');
                        return false;
                    }
                    if(this.signList[index].pageCount === null) {
                        self.$Message.error('文件页数必须输入');
                        return false;
                    }
                }
            }
            return true;
        },
        save: function () {
            this.isEdit = false;
            let self = this;
            let contract = JSON.parse(JSON.stringify(this.contract));
            contract.project.kind = this.project.kind;
            console.log(contract)
            // contract.signList = [];
            // for(let index in this.contract.signList){
            // 	contract.signList.push({
            // 		serialNo:this.contract.signList[index],
            // 		filingStatus:2
            // 	})
            // }

            if(!this.validate(false)) {
                return;
            }

            for(let index in this.signList){
                this.signList[index].filingStatus = 2;
            }
            contract.signList = this.signList;
            this.$Spin.show();
            this.$http.post("/filingApply/save", contract).then(function (response) {
            	this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档清单保存成功",
                        onClose: function () {
                            self.initDate();
                        },
                    });
                    window.close();
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
            	this.$Spin.hide();
                self.$Message.error(error);
            })
        },
        commit: function () {
            let self = this;
            let contract = JSON.parse(JSON.stringify(this.contract));
            contract.signList = [];
            for(let index in this.contract.signList){
            	contract.signList.push({
            		serialNo:this.contract.signList[index],
            		filingStatus:2
            	})
            }

            if(!this.validate(true)) {
                return;
            }
            contract.project.kind = this.project.kind;
            this.$Spin.show();
            this.$http.post("/filingApply/commit", contract).then(function (response) {
            	this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请提交成功",
                        onClose: function () {
                            window.close();
                        },
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
            	this.$Spin.hide();
                self.$Message.error(error);
            })
        },
        cancel: function () {
            this.removeAllFile();
        },
        indexOf: function (array, val) {
            for (let i = 0; i < array.length; i++) {
                if (array[i] == val) return i;
            }
            return -1;
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        },
        handleRemove : function(index) {
            this.signList.splice(index,1);
        },
        handleAdd : function() {
            console.log(this.contract.project.id)
            this.signList.push({
                projectId : this.contract.project.id,
                status: 0,
                signListName : '',
                isRequired : 2,
                type : 'add',
                pageCount : null,
                copies : null,
                getReady : false,
                remark : ''
            });
        },
        handleUp : function (index) {
            if(index === 0) {
                this.$Message.error("已经是第一行，不能再上移");
                return;
            }
            var tempOption = this.signList[index];
            var upOption = this.signList[index - 1];
            if(tempOption.category !== upOption.category) {
                this.$Message.error("不同分类不能移动");
                return;
            }
            this.$set(this.signList, index, upOption);
            this.$set(this.signList, index - 1, tempOption);

            console.log(this.signList);
        },
        handleDown : function (index) {
            if(index === this.signList.length - 1) {
                this.$Message.error("已经是最后行，不能再下移");
                return;
            }
            var tempOption = this.signList[index];
            var downOption = this.signList[index + 1];
            if(tempOption.category !== downOption.category) {
                this.$Message.error("不同分类不能移动");
                return;
            }
            this.$set(this.signList, index, downOption);
            this.$set(this.signList, index + 1, tempOption);
        }
    }
});
