/**
 * 菜单信息
 */
common.pageName = "meeting";
common.openName = [ '6' ];

var vue = new Vue({
	el : '#minutes',
	data : {
		meeting:{
			id:0,
			originator:{
				username:""
			}
		},
		editorContent:"",
		phone:"",
		statusItems : [],
		projects:[]
	},
	created : function() {
		this.initDate();
		this.getMeeting();
	},
	filters:{
		timeFilter:function(value){
			return moment(value).format('YYYY-MM-DD HH:mm');
		},
		statusFilter:function(value){
			for(var index in vue.statusItems){
				if(value == vue.statusItems[index].value){
					return vue.statusItems[index].text;
				}
			}
			return "";
		}
	},
	methods : {
		/**
		 * 初始化数据
		 */
		initDate : function() {
			var _self = this;
			this.$http.get("/combo/meetingStatus").then(function(response) {
				_self.statusItems = response.data;
			}, function(error) {
				console.error(error);
			})
			this.$http.get("/minutes/project").then(function(response){
				_self.projects = response.data;
			},function(error){
				console.error(error);
			})
		},
		getMeeting:function(){
			let _self = this;
			this.$http.get("/minutes/meeting/"+document.getElementById("meetingId").value).then(function(response) {
				_self.meeting = response.data;
				console.log(_self.meeting);
			}, function(error) {
				console.error(error);
			})
		}
	}
});