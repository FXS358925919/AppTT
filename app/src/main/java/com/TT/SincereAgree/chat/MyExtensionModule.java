package com.TT.SincereAgree.chat;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * Created by 冯雪松 on 2018-02-03.
 */

public class MyExtensionModule extends DefaultExtensionModule {
    private RedPacketPlugin redPacketPlugin;
    private GiftPlugin giftPlugin;
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
        pluginModules.add(new RedPacketPlugin());
        pluginModules.add(new GiftPlugin());
        pluginModules.add(new SendCountPlugin());
        return pluginModules;
    }

}
