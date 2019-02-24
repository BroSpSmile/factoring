
var app = new Vue({
	el : '#app',
	data : {
		form: {
            mobile: '',
            passwd: ''
	      },
	    ruleValidate:{
	    	mobile: [
                {required: true, message: '用户名不能为空', trigger: 'blur'}
            ],
            passwd:[
            	{required: true, message: '密码不能为空', trigger: 'blur'}
            ]
	    }
	},
	created : function() {
		
	},
	methods : {
		/**
		 * 登录
		 */
		login:function(){
			let self = this;
			this.$refs['loginForm'].validate((valid) => {
            	if (!valid) {
            		this.$Message.error('校验失败,请完善登录信息!');
            		passed = false;
            	}else{
            		this.$http.post("/login",this.form).then(function(response){
            			console.log(response.data);
            			if (response.data.success) {
            				window.location.href="index";
        				} else {
        					self.$Message.error(response.data.errorMessage);
        				}
            		},function(error){
            			self.$Message.error(error.data.message);
            		})
            	}
            })
		}
	}
});