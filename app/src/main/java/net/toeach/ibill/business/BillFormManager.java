package net.toeach.ibill.business;

import android.os.Handler;
import android.text.TextUtils;

import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import net.toeach.base.TException;
import net.toeach.common.utils.DateUtil;
import net.toeach.common.utils.StringUtil;
import net.toeach.ibill.dao.BillCategoryDao;
import net.toeach.ibill.dao.BillFormDao;
import net.toeach.ibill.dao.BillRecordDao;
import net.toeach.ibill.dao.BillSectionDao;
import net.toeach.ibill.dao.BillSectionRecordDao;
import net.toeach.ibill.model.BillCategory;
import net.toeach.ibill.model.BillDetailItem;
import net.toeach.ibill.model.BillForm;
import net.toeach.ibill.model.BillRecord;
import net.toeach.ibill.model.BillSection;
import net.toeach.ibill.model.BillSectionRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账单业务类
 */
public class BillFormManager extends BaseManager {
    public final static int MSG_SAVE_SUCCESS = 4001;// 保存成功
    public final static int MSG_DEL_SUCCESS = 4002;// 删除成功
    public final static int MSG_LIST_SUCCESS = 4003;// 获取列表成功
    public final static int MSG_GET_VALUE = 4004;// 获取列表成功


    private static BillFormManager instance = new BillFormManager();
    private BillFormDao dao;// dao对象

    /**
     * 私有构造函数
     */
    private BillFormManager() {
        dao = BillFormDao.getInstance();
    }

    /**
     * 返回实例对象
     */
    public static BillFormManager getInstance() {
        return instance;
    }

    /**
     * 判断月账单是否存在
     *
     * @param title   标题
     * @param handler Handler对象
     */
    public void isMonthlyFormExisted(String title, Handler handler) {
        try {
            BillForm ret = dao.findByTitle(title, 0);
            handleMessage(handler, MSG_GET_VALUE, ret);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 重新生成月账单记录
     *
     * @param id      账单标识
     * @param handler Handler对象
     */
    public void reCreateMonthlyForm(int id, Handler handler) {
        try {
            BillForm bean = dao.findById(id);
            saveMonthlyForm(bean, handler);
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 增加月账单记录
     *
     * @param bean    账单对象
     * @param handler Handler对象
     */
    public void saveMonthlyForm(BillForm bean, Handler handler) {
        if (TextUtils.isEmpty(bean.getTitle())) {// 账单标题不能为空
            handleError(handler, 1010);
            return;
        }
        LogUtils.d(bean.toString());

        try {
            dao.save(bean);// 保存主账单数据
            BillSectionDao.getInstance().deleteAllSections(bean.getId());// 删除对应子账单数据

            int parentId = bean.getId();
            // 提取指定月份的所有明细记录
            List<BillRecord> list = BillRecordDao.getInstance().getList(bean.getTitle());
            if (list != null) {
                LogUtils.d("month:" + bean.getTitle() + ", 对应的费用明细列表：");
                for (BillRecord record : list) {// 遍历费用明细列表
                    LogUtils.d(record.toString());

                    // 生成子账单信息
                    BillCategory cat = BillCategoryDao.getInstance().findById(record.getCatId());
                    if (cat != null) {
                        String sectionName = cat.getName();
                        BillSection section = BillSectionDao.getInstance().get(parentId, sectionName);
                        if (section == null) {// 创建新的子账单信息
                            section = new BillSection();
                            section.setParentId(parentId);
                            section.setTitle(sectionName);
                            section.setCreateTime(new Date());
                            BillSectionDao.getInstance().save(section);
                        }

                        // 插入子账单费用明细
                        BillSectionRecord bsr = new BillSectionRecord();
                        bsr.setSectionId(section.getId());
                        bsr.setRecordId(record.getId());
                        BillSectionRecordDao.getInstance().save(bsr);
                    }
                }
            }

            handleMessage(handler, MSG_SAVE_SUCCESS, bean);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 删除账单记录
     *
     * @param id      账单记录对象表示
     * @param handler Handler对象
     */
    public void deleteById(int id, Handler handler) {
        try {
            dao.deleteById(id);// 删除数据
            BillSectionDao.getInstance().deleteAllSections(id);// 删除子账单信息
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 批量删除账单记录
     *
     * @param list    账单记录对象列表
     * @param handler Handler对象
     */
    public void deleteAll(List<BillForm> list, Handler handler) {
        try {
            dao.deleteAll(list);// 删除数据
            handleMessage(handler, MSG_DEL_SUCCESS);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取账单记录
     *
     * @param pageNo   分页号
     * @param pageSize 分页数据大小
     * @param handler  Handler对象
     */
    public void getList(int pageNo, int pageSize, Handler handler) {
        try {
            List<BillForm> list = dao.getList(pageNo, pageSize);// 获取账单记录列表
            handleMessage(handler, MSG_LIST_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }

    /**
     * 获取月账单数据
     *
     * @param formId
     * @param handler
     */
    public void getMonthlyFormDetail(int formId, Handler handler) {
        try {
            List<BillDetailItem> list = new ArrayList<>();
            double total = 0;
            // 获取子账单列表
            List<BillSection> sections = BillSectionDao.getInstance().getList(formId);
            for (BillSection section : sections) {
                BillDetailItem item = new BillDetailItem();
                item.setType(0);
                item.setTitle(section.getTitle());
                list.add(item);

                // 获取子账单下对应的费用明细列表
                List<BillRecord> records = BillSectionRecordDao.getInstance().getList(section.getId());
                for (BillRecord record : records) {
                    BillDetailItem item2 = new BillDetailItem();
                    item2.setType(1);
                    item2.setMemo(record.getMemo());
                    double cost = record.getCost();
                    cost = cost / 100;
                    item2.setCost("￥" + StringUtil.formatMoney(String.valueOf(cost), 2));
                    try {
                        item2.setDate(DateUtil.formatDate(record.getBillDate()));
                    } catch (Exception e) {
                    }
                    list.add(item2);
                    total += record.getCost();
                }
            }

            // 加入合计项
            total = total / 100;
            BillDetailItem item = new BillDetailItem();
            item.setType(2);
            item.setCost("￥" + StringUtil.formatMoney(String.valueOf(total), 2));
            list.add(item);

            handleMessage(handler, MSG_LIST_SUCCESS, list);// 发送消息通知UI
        } catch (DbException e) {
            handleException(handler, new TException("Database occurs error."));// 抛出系统错误
        }
    }
}
