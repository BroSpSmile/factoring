var header = new Vue({
	el: '#header',
	data:{
		user:{},
        updatePasswordForm : {
			oldPassword : '',
			newPassword : '',
			confirmPassword : ''
		},
        ruleValidate: {
            oldPassword: [
                { required: true, message: '原密码不能为空', trigger: 'blur' }
            ],
            newPassword: [
                { required: true, message: '新密码不能为空', trigger: 'blur' }
            ],
            confirmPassword: [
                { required: true, message: '确认密码不能为空', trigger: 'blur' }
            ]
        },
        modal1 : false,
        isDisable : false
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
		},
        /**
		 * 密码修改
         */
        changeMenu : function (menuName) {
			if(menuName === 'updatePassword') {
				this.modal1 = true;
                this.isDisable = false;
			}
        },
        /**
		 * 密码修改
         */
        updatePassword : function () {
            this.isDisable = true;
        	let self = this;
            this.$refs.updatePasswordForm.validate((valid) => {
                if(valid) {
					this.$http.put("/user/updatePassword", this.updatePasswordForm).then(function(response) {
						if (response.data.success) {
							self.$Message.info({
								content : "更新密码成功",
								onClose : function() {
									self.cancelUpdatePassword();
								}
							});
						} else {
							self.isDisable = false;
							self.$Message.error(response.data.errorMessage);
						}
					}, function(error) {
                        self.isDisable = false;
						self.$Message.error(error.data.message);
					});
                } else {
                    this.isDisable = false;
        		}
        	});
		},
        /**
         * 取消密码修改
         */
        cancelUpdatePassword : function() {
            this.modal1 = false;
            this.$refs.updatePasswordForm.resetFields();
        }
	}
});