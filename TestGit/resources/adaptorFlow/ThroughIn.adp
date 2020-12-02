<?xml version="1.0" encoding="utf-8"?>
<flows xmlns="http://com/dc/esb/xmlbean/adaptor/flow">
	<flow name="ThroughIn"  location="in" flowType="adaptorFlow" description="穿透场景接入流程">
		<service id="MonitorStep1BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="IPCheckerService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="AccessCheckService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="FlowControlService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="ConsumerClientService" forcedExec="false" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="MonitorStep4BService" forcedExec="true" rule="" >
			<parameters>
			</parameters>
		</service>
		<service id="FlowRecycleService" forcedExec="true" rule="" >
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