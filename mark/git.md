git reset --hard HEAD
git clone ssh://git@gitlab.bojoy.net:528/p5_server/p5_cn_cn.git
ssh-keygen -t rsa -C “1363320658@qq.com"
git push origin HEAD:V1.7.1_2019 (如果不存在则会新建分支)
