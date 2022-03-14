<template>

     <div >
        <el-container>
            <el-header>
                <img class="mlogo" src="https://www.markerhub.com/dist/images/logo/markerhub-logo.png" alt="">
            </el-header>
            <el-main>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                    <el-form-item label="用户名:" prop="username">
                        <el-input v-model="ruleForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码:" prop="password">
                        <el-input type="password" v-model="ruleForm.password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
     </div>



</template>
<script>
    export default {
        data() {
            return {
                ruleForm: {
                    username: '梁伟康',
                    password: '111111',

                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'change' }
                    ]

                },
                imgSrc:require('../assets/homelogin.jpg')

            };

        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this;
                        this.$axios.post('/login',this.ruleForm).then(rsp=>{
                            const jwt = rsp.headers['authorization']
                            const userInfo = rsp.data.data
                            //将接口返回的jwt和用户信息存储在浏览器中
                            _this.$store.commit("SET_TOKEN",jwt)
                            _this.$store.commit("SET_USER_INFO",userInfo)
                                this.$notify({
                                    title:'欢迎',
                                    message: '尊贵的'+ userInfo.username + '用户,欢迎回来!',
                                    type: 'success'
                                });
                            _this.$router.push("/blogs")
                            console.log("登录成功!!!")
                        },
                        )
                    } else {
                        return false;
                    }
                });
            },

            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>
<style scoped>
    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        /*background-color: #E9EEF3;*/
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .mlogo{
        height: 60%;
        margin-top: 10px;
    }
    .demo-ruleForm{
        width: 600px;
        margin: 0 auto;
    }
    /*.background{*/
    /*    width:100%;*/
    /*    height:100%;  !**宽高100%是为了图片铺满屏幕 *!*/
    /*    z-index:-1;*/
    /*    position: absolute;*/
    /*}*/

</style>