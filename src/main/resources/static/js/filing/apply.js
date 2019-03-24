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
        contract:{
        	signList:[],
        	project:{
        		id:0
        	}
        },
        fileList:[],
        signList:[],
        isInitFileRow: false,
        projectUrl: 'filingProject?type=0'
    },
    created: function () {
        this.contract.project.id = document.getElementById("projectId").value;
        this.initDate();
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
        			 console.log(_self.signList);
        			 _self.contract.signList = [];
        			 for(let index in _self.signList){
        				 if(_self.signList[index].filingStatus >=2){
        					 _self.contract.signList.push(_self.signList[index].serialNo);
        				 }
        			 }
            		
            	 },function(error){
            		 console.error(error);
            	 })
            }
           
        },

        save: function () {
            let self = this;
            let contract = JSON.parse(JSON.stringify(this.contract));
            contract.signList = [];
            for(let index in this.contract.signList){
            	contract.signList.push({
            		serialNo:this.contract.signList[index],
            		filingStatus:2
            	})
            }
            this.$http.post("/filingApply/save", contract).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请保存成功",
                        onClose: function () {
                            self.initDate();
                        },
                    });
                    window.open(this.projectUrl, "_self");
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
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
            this.$http.post("/filingApply/commit", contract).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请提交成功",
                        onClose: function () {
                            window.close();
                        },
                    });
                    window.open(this.projectUrl, "_self");
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error);
            })
        },
       
        cancel: function () {
            this.removeAllFile();
            window.open(this.projectUrl, "_self");
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
        }
    }
});
