package com.sky.mychat.netty.executor;

import com.sky.mychat.netty.executor.base.RegisterExecutor;
import com.sky.mychat.netty.executor.base.SingleChatExecutor;
import com.sky.mychat.netty.executor.group.*;
import com.sky.mychat.netty.executor.room.*;
import com.sky.mychat.netty.handle.HeartBeatHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 执行者管理类
 *
 * @author tiankong
 * @date 2019/11/17 19:58
 */
@Component
public class ExecutorManager {
    private Map<Byte, Executor> commandTypeMap = new HashMap<>();

    @Resource
    private RegisterExecutor registerCommand;
    @Resource
    private HeartBeatHandler heartBeatExecutor;
    @Resource
    private SingleChatExecutor singleChatExecutor;
    @Resource
    private GroupCreateExecutor createGroupExecutor;
    @Resource
    private GroupListOnlineMembersExecutor listGroupOnlineMembersExecutor;
    @Resource
    private GroupListAllMembersExecutor listGroupAllMembersExecutor;
    @Resource
    private GroupJoinExecutor groupJoinExecutor;
    @Resource
    private GroupQuitExecutor groupQuitExecutor;
    @Resource
    private GroupChatExecutor groupChatExecutor;
    @Resource
    private CreateRoomExecutor createRoomExecutor;
    @Resource
    private EntryRoomExecutor entryRoomExecutor;
    @Resource
    private QuitRoomExecutor quitRoomExecutor;
    @Resource
    private ListRoomMembersExecutor listRoomMembersExecutor;
    @Resource
    private RoomChatMessage roomChatMessage;

    @PostConstruct
    public void init() {
        commandTypeMap.put(roomChatMessage.command, roomChatMessage);
        commandTypeMap.put(listRoomMembersExecutor.command, listRoomMembersExecutor);
        commandTypeMap.put(quitRoomExecutor.command, quitRoomExecutor);
        commandTypeMap.put(entryRoomExecutor.command, entryRoomExecutor);
        commandTypeMap.put(createRoomExecutor.command, createRoomExecutor);
        commandTypeMap.put(groupChatExecutor.command, groupChatExecutor);
        commandTypeMap.put(groupQuitExecutor.command, groupQuitExecutor);
        commandTypeMap.put(groupJoinExecutor.command, groupJoinExecutor);
        commandTypeMap.put(listGroupAllMembersExecutor.command, listGroupAllMembersExecutor);
        commandTypeMap.put(listGroupOnlineMembersExecutor.command, listGroupOnlineMembersExecutor);
        commandTypeMap.put(singleChatExecutor.command, singleChatExecutor);
        commandTypeMap.put(createGroupExecutor.command, createGroupExecutor);
        commandTypeMap.put(heartBeatExecutor.command, heartBeatExecutor);
        commandTypeMap.put(registerCommand.command, registerCommand);
    }

    public Executor getCommand(Byte type) {
        return commandTypeMap.get(type);
    }
}
