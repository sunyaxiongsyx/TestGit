<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="SFUnPackIn"  location="in" flowType="adaptorFlow" description="存储转发IN流程">
		<service id="StoreForwardIdentifyService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep1BService" forcedExec="true" rule="" >
		</service>
		<service id="UnpackService" forcedExec="false" rule="" >
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
		</service>
		<attributes>
		</attributes>
		<parameters>
		</parameters>
	</flow>
</flows>