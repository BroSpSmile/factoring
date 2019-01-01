var vue = new Vue({
	el: '#side',
	data:{
		activeName:"",
		openName:[]
	},
	created:function(){
		this.activeName=common.pageName;
		this.openName=common.openName;
	},
	methods:{
		
	}
});