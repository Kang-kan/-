<template>
    <div class="mcontaner">
        <Header></Header>
        <div class="block">
            <el-timeline>

                <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
                    <el-card>
                        <h4>
                            <router-link :to="{name: 'BlogDetail', params: {blogId: blog}}">
                                {{blog.title}}
                            </router-link>
                            <el-button type="danger" icon="el-icon-delete" style="float: right" circle @click="deleteHome(blog.id)"></el-button>
                        </h4>
                        <p>{{blog.description}}</p>
                    </el-card>
                </el-timeline-item>

            </el-timeline>

            <el-pagination class="mpage"
                           background
                           layout="prev, pager, next"
                           :current-page="currentPage"
                           :page-size="pageSize"
                           :total="total"
                           @current-change=page>
            </el-pagination>

        </div>

    </div>
</template>

<script>
    import Header from "@/components/Header";
    export default {
        name: "BlogHome",
        components: {Header},
        data() {
            return {
                blogs: {
                    m:''
                },
                currentPage: 1,
                total: 0,
                pageSize: 5
            }
        },
        methods: {
            page(currentPage) {
                const _this = this
                _this.$axios.post("/homeBlogs?currentPage=" + currentPage, currentPage,{
                    headers: {
                            "Authorization": localStorage.getItem("token")
                        }}
                        ).then(res => {
                    _this.blogs = res.data.data.records
                    for (const argument of _this.blogs) {
                        argument.status = 1
                    }
                    _this.currentPage = res.data.data.current
                    _this.total = res.data.data.total
                    _this.pageSize = res.data.data.size
                    console.log(444,_this.blogs)

                })
            },
            deleteHome(id){
                const _this = this;
                this.$confirm('此操作将永久删除该博客, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.get("/deleteBlog/"+id).then(rsp=>{
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        _this.page(1)
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });

                });

            }
        },
        created() {
           if (this.$store.getters.getUser){
               this.page(1)
           }else{
               this.$router.push("/login")
           }


        }
    }
</script>

<style scoped>

    .mpage {
        margin: 0 auto;
        text-align: center;
    }

</style>