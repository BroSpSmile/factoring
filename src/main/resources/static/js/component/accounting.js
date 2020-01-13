/**
 * 金额输入框
 * @Dependencies accounting.js
 */
Vue.component('currency-input', {
	template: `
    <input\
        ref="input"\
        v-bind:value="formatValue"\
        v-on:input="onInput($event.target.value)"\
        v-on:focus="selectAll"\
        v-on:blur="onBlur"\
      >
  `,
	props: {
		value: {
			type: Number,
			default: 0,
			desc:'数值'
		},
		symbol:{
			type:String,
			default:'',
			desc:'货币标识符'
		},
		decimal:{
			type:Number,
			default:2,
			desc:'小数位'
		}
	},
	data:function(){
		return {
			currencyValue:accounting.unformat(this.value),
			focused:false
		};
	},
	computed:{
		formatValue:function(){
			if(this.focused){
				return this.currencyValue;
			}else{
				return accounting.formatMoney(this.currencyValue,this.symbol,this.decimal);
			}
		}
	},
	methods: {
		onInput: function (value) {
			this.currencyValue = accounting.unformat(value);
			this.$emit('input', this.currencyValue);
		},
		onBlur: function () {
			this.focused = false;
		},
		selectAll: function (event) {
			this.focused = true;
			setTimeout(function () {
				event.target.select()
			}, 0)
		}
	}
});