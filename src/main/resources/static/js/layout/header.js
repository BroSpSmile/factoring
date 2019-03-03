var header = new Vue({
	el: '#header',
	data:{
		user:{}
	},
	created:function(){
		this.getUser();
	},
	methods:{
		getUser:function(){
			let _self = this;
			this.$http.get("/login/user").then(function(response){
				_self.user = response.data;
			},function(error){
				_self.$Message.error(error);
			})
		}
	}
});