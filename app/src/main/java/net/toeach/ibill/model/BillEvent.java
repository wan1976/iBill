package net.toeach.ibill.model;

import java.util.Map;

/**
 * EventBus事件对象.
 */
public class BillEvent {
    /**
     * 事件类型定义
     */
    public final static int EVENT_NET_STAT_QUERY = 1; // 网络状态查询
    public final static int EVENT_NET_STAT_RESULT = 2; // 网络状态查询结果
    public final static int EVENT_RELOAD_CAT = 3; // 重新加载分类数据
    public final static int EVENT_RELOAD_RECORD = 4; // 重新加载明细记录数据
    public final static int EVENT_RELOAD_BILL = 5; // 重新加载账单记录数据
    public final static int EVENT_UPDATE = 6; // 升级通知

    private int eventType;// 事件类型
    private Map<String, ?> data;// 附带数据

    /**
     * 构造函数
     */
    public BillEvent() {
    }

    /**
     * 构造函数
     *
     * @param eventType
     * @param data
     */
    public BillEvent(int eventType, Map<String, ?> data) {
        this.eventType = eventType;
        this.data = data;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Map<String, ?> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BillEvent{" +
                "eventType=" + eventType +
                ", data=" + data +
                '}';
    }
}
