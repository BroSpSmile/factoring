/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#factoring',
	data : {
		detail:{
			project:{
				projectName:"",
				model:"RECOURSE_RIGHT"
				
			},
			factoringInstallments:[{
				amount:0,
				installmentDate:new Date(),
				type:"FACTORING"
			}],
			assignee:0,
			receivable:0,
			dropAmount:0,
			totalFactoringFee:0,
			moneyBack:"false",
			invoicing:"false",
			paied:"false"
		},
		showLater:false,
		validata:{
			'project.projectName': [
            	{ required: true,  message: '项目名称不能为空',trigger:'blur'}
            ],
            creditor: [
            	{ required: true,  message: '让与人不能为空',trigger:'blur'}
            ],
            debtor: [
            	{ required: true,  message: '债务人不能为空',trigger:'blur'}
            ],
            baseContract: [
            	{ required: true,  message: '基础合同不能为空',trigger:'blur'}
            ],
            assignee: [
            	{ required: true,  message: '应收账款受让款不能为空'}
            ],
            receivable: [
            	{ required: true,  message: '应收账款不能为空'}
            ],
            totalFactoringFee: [
            	{ required: true, type:'number', message: '保理费不能为空'}
            ]
        }
	},
	created : function() {
		this.getProject();
	},
	methods : {
		getProject:function(){
			let _self = this;
			let id = document.getElementById("projectId").value;
			if(id){
				this.$http.get("/factoring/"+id).then(function(response){
					_self.detail = response.data;
					for(let i in _self.detail.factoringInstallments){
						if(_self.detail.factoringInstallments[i].amount==0&&i==_self.detail.factoringInstallments.length-1){
							_self.detail.factoringInstallments[i].later =true;
							_self.showLater = true;
						}else{
							_self.detail.factoringInstallments[i].later =false;
						}
					}
				},function(error){
					console.error(error);
				})
			}
		},
		
		/** 添加保理费分期 */
		add:function(){
			for(let index in this.detail.factoringInstallments){
				this.detail.factoringInstallments[index].later =false;
			}
			this.detail.factoringInstallments.push({
				amount:0,
				installmentDate:new Date(),
				type:"FACTORING"
			});
			this.computeFee();
		},
		
		/**
		 * 移除
		 */
		remove:function(index){
			this.detail.factoringInstallments.splice(index,1);
			this.computeFee();
			for(let i in this.detail.factoringInstallments){
				this.showLater = false;
				if(this.detail.factoringInstallments[i].later){
					this.showLater = true;
					break;
				}
			}
			
		},
		
		/**
		 * 后补
		 */
		changeInstallment:function(installment){
			installment.amount = 0;
			if(installment.later){
				this.showLater =true;
				installment.installmentDate = null;
			}else{
				this.showLater =false;
				installment.installmentDate = new Date();
			}
			this.computeFee();
		},
		
		/** 计算保理费分期 */
		computeFee:function(){
			var total = 0;
			for(let index in this.detail.factoringInstallments){
				if(!this.detail.factoringInstallments[index].later){
					total += this.detail.factoringInstallments[index].amount;
				}
			}
			this.detail.totalFactoringFee = total;
		},
		
		/**  */
		submit:function(){
			let _self = this;
			this.$refs['entityDataForm'].validate((valid) => {
				if(!valid){
					_self.$Message.error('校验失败,请完善信息!');
					return false;
				}else{
					_self.commit();
				}
			});
		},
		
		/**
		 * 提交
		 */
		commit:function(){
			let self = this;
			this.$Spin.show();
			if(!this.detail.id){
				this.$http.post("/factoring", this.detail).then(function(response) {
					this.$Spin.hide();
					if (response.data.success) {
						self.$Message.info({
							content : "保存成功",
							onClose : function() {
								self.close();
							}
						});
					} else {
						self.$Message.error(response.data.errorMessage);
					}
				}, function(error) {
					this.$Spin.hide();
					self.$Message.error(error.data.message);
				});
			}else{
				this.$http.put("/factoring", this.detail).then(function(response) {
					this.$Spin.hide();
					if (response.data.success) {
						self.$Message.info({
							content : "保存成功",
							onClose : function() {
								self.close();
							}
						});
					} else {
						self.$Message.error(response.data.errorMessage);
					}
				}, function(error) {
					this.$Spin.hide();
					self.$Message.error(error.data.message);
				});
			}
		
		},
		
		/**
		 * formatter
		 */
		formatter:function(value){
			value = value+'';
			var intSum = value.replace( /\B(?=(?:\d{3})+$)/g, ',' );
			return '￥'+intSum;
		},
		
		/**
		 * parser
		 */
		parser:function(value){
			value = ""+value;
			return value.replace(/￥s?|(,*)/g, '')
		},
		
		close:function(){
		//	console.log($(window.parent.document).contents().find("iframe:first").html));
			//$('[data-id="/project"]', parent.document).parent().attr('src', $('[data-id="/project"]').attr('src'));
		}
	}
});
