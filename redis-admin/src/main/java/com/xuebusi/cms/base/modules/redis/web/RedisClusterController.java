package com.xuebusi.cms.base.modules.redis.web;

import com.xuebusi.cms.base.modules.redis.entity.RedisCluster;
import com.xuebusi.cms.base.modules.redis.service.RedisClusterService;
import com.xuebusi.cms.common.config.Global;
import com.xuebusi.cms.common.persistence.Page;
import com.xuebusi.cms.common.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 集群配置Controller
 *
 * @author shiyanjun
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/redis/redisCluster")
public class RedisClusterController extends BaseController {

    @Autowired
    private RedisClusterService redisClusterService;

    @ModelAttribute
    public RedisCluster get(@RequestParam(required = false) String id) {
        RedisCluster entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = redisClusterService.get(id);
        }
        if (entity == null) {
            entity = new RedisCluster();
        }
        return entity;
    }

    @RequiresPermissions("redis:redisCluster:view")
    @RequestMapping(value = {"list", ""})
    public String list(RedisCluster redisCluster, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RedisCluster> page = redisClusterService.findPage(new Page<RedisCluster>(request, response), redisCluster);
        model.addAttribute("page", page);
        return "base/modules/redis/redisClusterList";
    }

    @RequiresPermissions("redis:redisCluster:view")
    @RequestMapping(value = "form")
    public String form(RedisCluster redisCluster, Model model) {
        model.addAttribute("redisCluster", redisCluster);
        return "base/modules/redis/redisClusterForm";
    }

    @RequiresPermissions("redis:redisCluster:edit")
    @RequestMapping(value = "save")
    public String save(RedisCluster redisCluster, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, redisCluster)) {
            return form(redisCluster, model);
        }
        redisClusterService.save(redisCluster);
        addMessage(redirectAttributes, "保存集群配置成功");
        return "redirect:" + Global.getAdminPath() + "/redis/redisCluster/?repage";
    }

    @RequiresPermissions("redis:redisCluster:edit")
    @RequestMapping(value = "delete")
    public String delete(RedisCluster redisCluster, RedirectAttributes redirectAttributes) {
        redisClusterService.delete(redisCluster);
        addMessage(redirectAttributes, "删除集群配置成功");
        return "redirect:" + Global.getAdminPath() + "/redis/redisCluster/?repage";
    }

}