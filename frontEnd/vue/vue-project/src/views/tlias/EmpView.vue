// 页面原型
<template>
  <div>
    <el-container style="height: 700px; border: 1px solid #eee">
      <el-header
        style="
          font-size: 40px;
          background-color: rgb(238, 241, 246);
          text-align: center;
        "
        >学习辅助系统</el-header
      >
      <el-container>
        <el-aside width="200px" style="border: 1px solid #eee">
          <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
              <template slot="title"
                ><i class="el-icon-message"></i>系统信息管理</template
              >
              <el-menu-item index="1-1">
                <router-link to="/dept">部门管理</router-link>
                </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/emp">员工管理</router-link>
                </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-main>
          <el-form :inline="true" :model="formSearch" class="demo-form-inline">
            <el-form-item label="姓名">
              <el-input v-model="formSearch.name" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-select v-model="formSearch.gender" placeholder="性别">
                <el-option label="男" value="1"></el-option>
                <el-option label="女" value="2"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="日期">
              <el-date-picker
                v-model="formSearch.entryDate"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
          </el-form>

          <el-table :data="tableData" border>
            <el-table-column
              prop="name"
              label="姓名"
              width="180"
            ></el-table-column>
            <el-table-column
              label="图像"
              width="180">
            <template slot-scope="scope"> 
            <img :src="scope.row.image" width="100px" height="70px">
            </template>
            </el-table-column>
            <el-table-column
              label="性别"
              width="140" >
            <template slot-scope="scope"> 
            {{scope.row.gender==1?'男':'女'}}
            </template>
            </el-table-column>
            <el-table-column
              prop="job"
              label="职位"
              width="140"
            ></el-table-column>
            <el-table-column
              prop="entrydate"
              label="入职日期"
              width="180"
            ></el-table-column>
            <el-table-column
              prop="updatetime"
              label="最后操作时间"
              width="230"
            ></el-table-column>
            <el-table-column label="操作">
              <el-button type="primary" size="mini">编辑</el-button>
              <el-button type="danger" size="mini">删除</el-button>
            </el-table-column>
          </el-table>

          <br />
          <br />

          <el-pagination
            background
            layout="total,sizes,prev, pager, next,jumper"
            :total="1000"
          ></el-pagination>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


//数据模型和js
<script>
import axios from "axios";
export default {
  //这里就是vue的数据模型data
  data() {
    return {
      tableData: [],
      formSearch: {
        name: "",
        gender: "",
        entryDate: ["", ""],
      },
    };
  },
  methods: {
    onSubmit: function () {
      alert("提交成功");
    },
  },


  mounted() {
    axios
      .get("https://mock.apifox.cn/m1/3128855-0-default/emp/list")
      .then((result) => {
        this.tableData = result.data.data;
      });
  },
};
</script>


//样式
<style>
h1 {
  text-align: center;
}
</style>