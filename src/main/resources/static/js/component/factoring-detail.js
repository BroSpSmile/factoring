/**
 * 
 */
Vue.component('factoring-detail',{
	template:`
	<Row>
		<Col span='24'><span style='color:#2db7f5'>基础合同:</span><span>{{factoring.baseContract}}</span></Col>
		<Divider size='small'></Divider>
		<Col span='6'><span style='color:#2db7f5'>签署日期:</span><span>{{factoring.signDate?moment(factoring.signDate).format('YYYY-MM-DD'):""}}</span></Col>
		<Col span='6'><span style='color:#2db7f5'>合同回款日:</span><span>{{factoring.remittanceDay?moment(factoring.remittanceDay).format('YYYY-MM-DD'):""}}</span></Col>
		<Col span='6'><span style='color:#2db7f5'>收益率:</span><span>{{factoring.returnRate}} %</span></Col>
		<Col span='24'><Divider size='small'></Divider></Col>
		<Row v-if="factoring.loanInstallments&&factoring.loanInstallments.length>1">
			<Col span='24'><span style='color:#2db7f5'>已投放金额:</span><span>{{factoring.dropAmount}}</span></Col>
			<Col span='8'><Table  :columns="columns1" :data="factoring.loanInstallments"></Table></Col>
			<Col span='24'><Divider size='small'></Divider></Col>
		</Row>
		<Row v-if="factoring.returnInstallments&&factoring.returnInstallments.length>1">
			<Col span='24'><span style='color:#2db7f5'>回款信息:</span></Col>
			<Col span='12'><Table  :columns="columns2" :data="factoring.returnInstallments" ></Table></Col>
			<Col span='24'><Divider size='small'></Divider></Col>
		</Row>
		<Row v-if="factoring.factoringInstallments&&factoring.factoringInstallments.length>1">
			<Col span='24'><span style='color:#2db7f5'>保理费合计:</span><span>{{factoring.totalFactoringFee}}</span></Col>
			<Col span='18'><Table :columns="columns3" :data="factoring.factoringInstallments"></Table></Col>
			<Col span='24'><Divider size='small'></Divider></Col>
		</Row>
		<Col span='24'><span style='color:#2db7f5'>备注:</span><span></span></Col>
	</Row>
	`,
	props:{
		row:Object
	},
	created:function(){
		this.getProject();
	},
	methods:{
		getProject:function(){
			let _self = this;
			this.$http("/factoring/"+this.row.id).then(function(response){
				_self.factoring = response.data;
			},function(error){
				console.error(error);
			})
		}
	},
	data:function(){
		return {
			factoring:{},
			columns1:[{
					title: '已投放金额(元)',
					align: 'center',
					key: 'amount'
				},{
					title: '放款日',
					align: 'center',
					width:200,
					render:(h,params)=>{
						return h('span',params.row.installmentDate?moment(params.row.installmentDate).format('YYYY-MM-DD'):"")
					}
				}],
			columns2:[{
					title: '回款金额(元)',
					align: 'center',
					width:200,
					key: 'amount'
				},{
					title: '是否已回款',
					align: 'center',
					render:(h,params)=>{
						if(params.row.paied){
							return h('span',"是");
						}
						return h('span',"否");
					}
				},{
					title: '实际回款日',
					align: 'center',
					width:200,
					render:(h,params)=>{
						return h('span',params.row.installmentDate?moment(params.row.installmentDate).format('YYYY-MM-DD'):"")
					}
				}],
			columns3:[{
					title: '保理费分期金额(元)',
					align: 'center',
					width:200,
					key: 'amount'
				},{
					title: '保理费到账日(前)',
					align: 'center',
					render:(h,params)=>{
						return h('span',params.row.installmentDate?moment(params.row.installmentDate).format('YYYY-MM-DD'):"")
					}
				},{
					title: '已开发票',
					align: 'center',
					width:200,
					render:(h,params)=>{
						if(params.row.invoice){
							return h('span',"是");
						}
						return h('span',"否");
					}
				},{
					title: '是否已支付',
					align: 'center',
					width:200,
					render:(h,params)=>{
						if(params.row.paied){
							return h('span',"是");
						}
						return h('span',"否");
					}
				}]
		}
	}
});