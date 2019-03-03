/**
 * 菜单信息
 */
common.pageName = "audits";
common.openName = [ 'audits' ];

var vue = new Vue({
	el : '#audits',
	data : {
		auditType:[],
		auditResult:[],
		formInline:{}
	},
	created : function() {
		
	},
	methods : {
		
		/**
		 * 初始化
		 */
		initDate:function(){
			let _self = this;
			this.$http.get("/combo/auditType").then(function(response) {
				_self.auditType = response.data;
			}, function(error) {
				console.error(error);
			});
			this.$http.get("/combo/auditResult").then(function(response) {
				_self.auditResult = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		search:function(){
			
		},
		
		reset:function(){
			
		}
	}
});

