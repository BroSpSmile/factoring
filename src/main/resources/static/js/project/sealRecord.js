/**
 * 用印管理
 */
common.pageName = "sealRecord";
common.openName = [ '3' ];

var vue = new Vue({
    el : '#sealRecord',
    data : {
        formInline:{
            type : []
        },
        queryParam : {
            condition : {},
            pageNum : 1,
            pageSize : 10
        },
        addForm : {
            signList : []
        },
        pageInfo:{},
        tableColumns:[],
        showResult:false,
        modal1:false
    },
    created : function() {
        this.initData();
        this.query();
    },
    methods : {
        /**
         * 初始化数据
         */
        initData : function() {
        },
        /** 分页 */
        pageChange : function(page){
            this.queryParam.pageNum = page;
            this.query();
        },
        /**
         * 查询
         */
        query : function(){
            this.showResult=true;
            var _self = this;
            this.queryParam.condition = this.formInline;
            this.$http.post("/sealRecord/list", this.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 取消保存
         */
        cancel : function() {
            this.modal1 = false;
        },
        /**
         * 重置
         */
        reset: function () {
            this.$refs['searchForm'].resetFields();
        },
        /**
         * 状态转换
         * @param value
         */
        getStatusDesc : function(value) {
            if(value === 0) {
                return "未用印";
            } else if(value === 1) {
                return "已用印";
            }
            return "";
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        },
        toAudit : function(auditId){
            parent.window.menu.createNew({
                name:"审核",
                url:"/audit?id="+auditId,
                id:"audit"
            });
        }
    }
});

vue.tableColumns=[
    {
        title: '项目编号',
        key: 'projectCode',
        align: 'left'
    },{
        title: '项目名称',
        key: 'projectName',
        align: 'left'
    },{
        title: '业务负责人',
        key: 'projectPerson',
        align: 'left'
    },{
        title: '用印完成时间',
        key: 'sealFinishTime',
        align: 'left',
        render:(h,param)=>{
            return h('span',moment(param.row.sealFinishTime).format('YYYY-MM-DD HH:mm:ss'))
        }
    },{
        title: '用印状态',
        key: 'sealStatus',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getStatusDesc(param.row.sealStatus));
        }
    }
];