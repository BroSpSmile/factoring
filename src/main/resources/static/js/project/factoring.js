/**
 * 菜单信息
 */
common.pageName = "approval";
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
		}
	},
	created : function() {
	},
	methods : {
		add:function(){
			this.detail.factoringInstallments.push({
				amount:0,
				installmentDate:new Date(),
				type:"FACTORING"
			});
		},
		
		/**
		 * 移除
		 */
		remove:function(index){
			this.detail.factoringInstallments.splice(index,1);
		},
		
		/** 计算保理费分期 */
		computeFee:function(){
			var total = 0;
			for(let index in this.detail.factoringInstallments){
				total += this.detail.factoringInstallments[index].amount;
			}
			this.detail.totalFactoringFee = total;
		},
		
		commit:function(){
			console.log(this.project);
			let self = this;
			this.$http.post("/factoring", this.detail).then(function(response) {
				if (response.data.success) {
					self.$Message.info({
						content : "保存成功",
						onClose : function() {
							
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			}, function(error) {
				self.$Message.error(error.data.message);
			});
		}
	}
});
