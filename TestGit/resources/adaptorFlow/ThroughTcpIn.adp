<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="ThroughTcpIn"  location="in" flowType="adaptorFlow" description="">
		<service id="ServiceIdentify" forcedExec="false" rule="TcpRole" >
			<parameters>
			</parameters>
		</service>
		<service id="ConsumerClientService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<attributes>
			<attribute owner="common" key="through" value="true"/>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>