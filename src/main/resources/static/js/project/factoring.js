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
			assignee:0,
			receivable:0,
			dropAmount:0,
			moneyBack:"false",
			invoicing:"false",
			paied:"false"
		}
	},
	created : function() {
	},
	methods : {
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
