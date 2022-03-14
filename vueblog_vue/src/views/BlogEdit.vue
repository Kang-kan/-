<template>
    <div>
     <Header></Header>
        <div class="m_mavon">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="ruleForm.title"></el-input>
                </el-form-item>
                <el-form-item label="摘要" prop="description">
                    <el-input v-model="ruleForm.description"></el-input>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <mavon-editor v-model="ruleForm.content"></mavon-editor>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>

    import Header from "@/components/Header";
    export default {
        name: "BlogEdit",
        components: {Header},
        data() {
            return {
                ruleForm: {
                    id:'',
                    title: '',
                    description: '',
                    content: '',
                },
                rules: {
                    title: [
                        { required: true, message: '请输入标题称', trigger: 'blur' },
                        { min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: '请输入摘要', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ]
                }
            };
        },
        created() {
            if (this.$route.params.blogId){
                const blogId = this.$route.params.blogId
                const _this = this;
                this.$axios.get("/blog/"+blogId).then(rsp=>{
                    _this.ruleForm.id = rsp.data.data.id
                    _this.ruleForm.title = rsp.data.data.title
                    _this.ruleForm.description = rsp.data.data.description
                    _this.ruleForm.content = rsp.data.data.content
                })
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        this.$axios.post("/blog/edit",this.ruleForm,{
                            headers: {
                                "Authorization": localStorage.getItem("token")
                            }
                        }).then(rsp=>{
                            _this.$alert('操作成功', '提示', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    _this.$router.push("/home")
                                }
                            });
                        })
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
    .m_mavon{
        margin: 0 auto;
        text-align: center;
    }
</style>