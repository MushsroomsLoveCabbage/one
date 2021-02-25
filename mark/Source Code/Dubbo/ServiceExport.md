## 服务注册

### 核心类

* ServiceBean -> Spring bean 

#### 流程

```java
ServiceBean.onApplicationEvent(); 
ServiceConfig.export() {
        checkAndUpdateSubConfigs();
        if (provider != null) {
            if (export == null) {
                export = provider.getExport();
            }
            if (delay == null) {
                delay = provider.getDelay();
            }
        }
        if (export != null && !export) {
            return;
        }
        //延迟暴露
        if (delay != null && delay > 0) {
            delayExportExecutor.schedule(this::doExport, delay, TimeUnit.MILLISECONDS);
        } else {
            doExport();
        }
    }
```

