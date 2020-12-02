<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="SFTrIn"  location="in" flowType="adaptorFlow" description="存储转发接入渠道-穿透">
		<service id="StoreForwardIdentifyService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep1BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="ConsumerClientService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="GeneratePkgService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep4BService" forcedExec="true" rule="" >
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