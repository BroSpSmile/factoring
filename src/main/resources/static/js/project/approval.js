/**
 * 菜单信息
 */
common.pageName="approval";
common.openName=['1'];

var vue = new Vue({
	el: '#approval',
	data:{
		formInline:{
			projectId:"",
			projectName:"",
			projectOwner:"",
			status:""
		},
		statusItems:[
			"apply",
			"examine",
			"pastmeeting",
			"drawup",
			"contractaudit",
			"sign",
			"loan",
			"loanaudit",
			"file"
		],
	},
	created:function(){
		
	},
	methods:{
		
	}
});