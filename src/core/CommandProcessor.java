package core;

import model.Abone;
import model.CommandType;

public class CommandProcessor {
    public static String process(CommandType command, Abone localAbone, String host, int port, int port1,int serverId) {
        switch (command) {
            case ABONOL -> {
                return CommandProcessor.processAbonol(localAbone, host, port, port1,serverId);
            }

            case ABONIPTAL -> {
                return CommandProcessor.processAbonIptal(localAbone, host, port, port1,serverId);
            }

            case GIRIS -> {
                return CommandProcessor.processGiris(localAbone, host, port, port1,serverId);
            }

            case CIKIS -> {
                return CommandProcessor.processCikis(localAbone, host, port, port1,serverId);
            }
            default -> {
                return "50 HATA";
            }
        }
    }

    private static String processAbonol(Abone localAbone, String host, int port, int port1, int serverId ) {
        if (localAbone.getAbone()) {
            return "50 HATA";
        } else {
            localAbone.setAbone(true);
            localAbone.setLastUpdatedEpochMiliSeconds(System.currentTimeMillis());
            ServerHandler.Send(host, port, port1, localAbone, serverId);
            return "55 TAMM";
        }
    }

    private static String processAbonIptal(Abone locaLAbone, String host, int port, int port1, int serverId) {
        if (locaLAbone.getAbone()) {
            locaLAbone.setAbone(false);
            locaLAbone.setLastUpdatedEpochMiliSeconds(System.currentTimeMillis());
            ServerHandler.Send(host, port, port1, locaLAbone, serverId);
            return "55 TAMM";
        } else {
            return "50 HATA";
        }
    }

    private static String processGiris(Abone locaLAbone, String host, int port, int port1, int serverId) {
        if (locaLAbone.getGiris() || !locaLAbone.getAbone()) {
            return "50 HATA";
        } else if(locaLAbone.getAbone()) {
            locaLAbone.setGiris(true);
            locaLAbone.setLastUpdatedEpochMiliSeconds(System.currentTimeMillis());
            ServerHandler.Send(host, port, port1, locaLAbone, serverId);
            return "55 TAMM";
        }
        else
            return "50 HATA";
    }

    private static String processCikis(Abone locaLAbone, String host, int port, int port1, int serverId) {
        if (locaLAbone.getGiris()) {
            locaLAbone.setGiris(false);
            locaLAbone.setLastUpdatedEpochMiliSeconds(System.currentTimeMillis());
            ServerHandler.Send(host, port, port1, locaLAbone, serverId);
            return "55 TAMM";
        } else {
            return "50 HATA";
        }
    }
}
