wifi.setmode(wifi.STATION)
wifi.sta.config("nameOfYourWifi","passwordOfYourWifi")
server = net.createServer(net.TCP) 
server:listen(80, function(con)
    con:on("receive", function(socket, receivedData) 
        xtype = type(tonumber(receivedData))
        xnumber = tonumber(receivedData)
        xresult = xnumber * 2
        print("received: " .. xtype)  
        print("received * 2: " .. xresult) 
        socket:close()                           
    end) 
end)
