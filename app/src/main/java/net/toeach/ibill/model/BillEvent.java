package net.toeach.ibill.model;

import java.util.Map;

/**
 * EventBus事件对象.
 */
public class BillEvent {
    /**
     * 事件类型定义
     */
    public enum EventType {
        EVENT_NET_STAT_QUERY, // 网络状态查询
        EVENT_NET_STAT_RESULT, // 网络状态查询结果
        EVENT_RELOAD;// 重新加载数据
    }

    private EventType eventType;// 事件类型
    private Map<String, ?> data;// 附带数据

    /**
     * 构造函数
     */
    public BillEvent() {
    }

    /**
     * 构造函数
     * @param eventType
     * @param data
     */
    public BillEvent(EventType eventType, Map<String, ?> data) {
        this.eventType = eventType;
        this.data = data;
    }

    public EventType getEventType() {
        return eventType;
    }
    public void setEventType(EventType eventType) {
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
