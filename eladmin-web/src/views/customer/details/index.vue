<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">客户账号</label>
        <el-input v-model="query.customerNum" clearable placeholder="客户账号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">客户名称</label>
        <el-input v-model="query.customerName" clearable placeholder="客户名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">邀请码</label>
        <el-input v-model="query.invitationCode" clearable placeholder="邀请码" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <date-range-picker
          v-model="query.createTime"
          start-placeholder="createTimeStart"
          end-placeholder="createTimeStart"
          class="date-item"
        />
        <date-range-picker
          v-model="query.updateTime"
          start-placeholder="updateTimeStart"
          end-placeholder="updateTimeStart"
          class="date-item"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="客户账号" prop="customerNum">
            <el-input v-model="form.customerNum" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="客户名称" prop="customerName">
            <el-input v-model="form.customerName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="邀请码" prop="invitationCode">
            <el-input v-model="form.invitationCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="customerNum" label="客户账号" />
        <el-table-column prop="customerName" label="客户名称" />
        <el-table-column prop="invitationCode" label="邀请码" />
        <el-table-column prop="password" label="密码" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column prop="createBy" label="创建人" />
        <el-table-column prop="updateBy" label="最后更新人" />
        <el-table-column v-if="checkPer(['admin','uniCustomer:edit','uniCustomer:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudUniCustomer from '@/api/uniCustomer'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { customerId: null, customerNum: null, customerName: null, invitationCode: null, password: null, createTime: null, updateTime: null, createBy: null, updateBy: null }
export default {
  name: 'UniCustomer',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '客户信息', url: 'api/uniCustomer', idField: 'customerId', sort: 'customerId,desc', crudMethod: { ...crudUniCustomer }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'uniCustomer:add'],
        edit: ['admin', 'uniCustomer:edit'],
        del: ['admin', 'uniCustomer:del']
      },
      rules: {
        customerNum: [
          { required: true, message: '客户账号不能为空', trigger: 'blur' }
        ],
        customerName: [
          { required: true, message: '客户名称不能为空', trigger: 'blur' }
        ],
        invitationCode: [
          { required: true, message: '邀请码不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'customerNum', display_name: '客户账号' },
        { key: 'customerName', display_name: '客户名称' },
        { key: 'invitationCode', display_name: '邀请码' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
