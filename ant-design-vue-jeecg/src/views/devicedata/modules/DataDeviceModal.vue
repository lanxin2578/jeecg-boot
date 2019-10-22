<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入设备名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="设备状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['status']" :trigger-change="true" dictCode="device_status" placeholder="请选择设备状态"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="设备遥测数据" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="tsKvTable.loading"
            :columns="tsKvTable.columns"
            :dataSource="tsKvTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
        <a-tab-pane tab="设备数据" :key="refKeys[1]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[1]"
            :loading="tsKvLatestTable.loading"
            :columns="tsKvLatestTable.columns"
            :dataSource="tsKvLatestTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'DataDeviceModal',
    mixins: [JEditableTableMixin],
    components: {
      JDictSelectTag,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          name: { rules: [{ required: true, message: '请输入设备名称!' }] },
          status: { rules: [{ required: true, message: '请输入设备状态!' }] },
        },
        refKeys: ['tsKv', 'tsKvLatest', ],
        tableKeys:['tsKv', 'tsKvLatest', ],
        activeKey: 'tsKv',
        // 设备遥测数据
        tsKvTable: {
          loading: false,
          dataSource: [],
          columns: [
          ]
        },
        // 设备数据
        tsKvLatestTable: {
          loading: false,
          dataSource: [],
          columns: [
          ]
        },
        url: {
          add: "/devicedata/dataDevice/add",
          edit: "/devicedata/dataDevice/edit",
          tsKv: {
            list: '/devicedata/dataDevice/queryTsKvByMainId'
          },
          tsKvLatest: {
            list: '/devicedata/dataDevice/queryTsKvLatestByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'name','status','delFlag','createBy','createTime')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.tsKv.list, params, this.tsKvTable)
          this.requestSubTableData(this.url.tsKvLatest.list, params, this.tsKvLatestTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          tsKvList: allValues.tablesValue[0].values,
          tsKvLatestList: allValues.tablesValue[1].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'name','status','delFlag','createBy','createTime'))
     },

    }
  }
</script>

<style scoped>
</style>