<template>
    <div>
        <Header></Header>
        <div class="m_blogE">
            <el-button type="warning" round size="mini" style="float: right" @click="retrueOpen">返回上一级</el-button>
                <h3>{{userBlogs.title}}</h3>
            <el-link icon="el-icon-edit" v-if="openBlogs">
                <router-link :to="{name:'BlogEdit',pragma:{blogId: userBlogs.id}}" >
                    编辑
                </router-link>
            </el-link>
            <el-divider/>
            <div class="markdown-body" v-html="userBlogs.content"/>
        </div>
    </div>
</template>

<script>
    import 'github-markdown-css';
    import Header from "@/components/Header";
    export default {
        name: "BlogDetail",
        components: {Header},
        data(){
            return {
                userBlogs:{
                    id:'',
                    title:'11111',
                    description:'',
                    content:''
                },
                openBlogs:false,

            }
        },

        created() {
            const blogId = this.$route.params.blogId.id;
                const _this = this;
                const userId = this.$store.getters.getUser.id;
                this.$axios.get("/blog/"+ blogId).then(rsp=>{
                    const blogs = rsp.data.data;
                    _this.userBlogs.id =  blogs.id;
                    _this.userBlogs.title =  blogs.title;
                    _this.userBlogs.description =  blogs.description;
                    var MardownIt = require("markdown-it");//引用
                    var md = new MardownIt();//解密工具
                    var result = md.render(blogs.content);//解密
                    _this.userBlogs.content = result ;//回显
                    _this.openBlogs = userId==blogs.userId?true:false;
                })


        },
        methods:{
            retrueOpen(){
               const status = this.$route.params.blogId.status;
                console.log(1111,this.$route.params.blogId.status)
                if (status===1){
                    this.$router.push("/home")
                }
                if (status===0){
                    this.$router.push("/blogs")
                }

            }
        }
    }
</script>

<style scoped>
    .m_blogE{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 100%;
        min-height: 700px;
        padding: 20px 15px;
    }
</style>